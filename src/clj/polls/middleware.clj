(ns polls.middleware
  (:require
    [polls.env :refer [defaults]]
    [clojure.tools.logging :as log]
    [polls.layout :refer [error-page]]
    [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
    [polls.middleware.formats :as formats]
    [muuntaja.middleware :refer [wrap-format wrap-params]]
    [polls.config :refer [env]]
    [ring.middleware.flash :refer [wrap-flash]]
    [immutant.web.middleware :refer [wrap-session]]
    [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
    [ring.util.http-response :as response])
  )

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (log/error t (.getMessage t))
        (error-page {:status 500
                     :title "Something very bad has happened!"
                     :message "We've dispatched a team of highly trained gnomes to take care of the problem."})))))

(defn wrap-csrf [handler]
  (wrap-anti-forgery
    handler
    {:error-response
     (error-page
       {:status 403
        :title "Invalid anti-forgery token"})}))


(defn wrap-formats [handler]
  (let [wrapped (-> handler wrap-params (wrap-format formats/instance))]
    (fn [request]
      ;; disable wrap-formats for websockets
      ;; since they're not compatible with this middleware
      ((if (:websocket? request) handler wrapped) request))))

(defn wrap-auth [handler]
  (fn [request]
    (let [response (handler request)]
      (if (contains? (request :session) :user)
        response
        (response/found (str "/login?redirect=" (request :path-info) "?" (request :query-string)))))))

(defn wrap-base [handler]
  (-> ((:middleware defaults) handler)
      wrap-flash
      (wrap-session {:cookie-attrs {:http-only true}})
      (wrap-defaults
        (-> site-defaults
            (assoc-in [:security :anti-forgery] false)
            (dissoc :session)))
      wrap-internal-error))
