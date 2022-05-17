(ns polls.routes.auth
  (:require
   [polls.layout :as layout]
   [polls.auth :as auth]
   [polls.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

(defn login-page [request]
  (let [redirect ((request :query-params) "redirect")]
    (layout/render request "login.html" {:redirect redirect})
    )
  )

(defn login-attempt [request]
  (let [{:keys [username password]} (request :params)
        redirect ((request :query-params) "redirect")
        session (request :session)
        ]
    (def authresult (auth/authenticate-user username password))
    (if authresult
      (-> (response/found redirect)
          (assoc :session (assoc session :user (authresult :login))))
      (layout/render request "login.html" {:error (str "Authentication Failure") :redirect redirect})
      )
    )
  )

(defn auth-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/login" {:get login-page
              :post login-attempt}]
   ]
  )
