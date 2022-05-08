(ns polls.routes.home
  (:require
   [polls.layout :as layout]
   [polls.db.core :as db]
   [clojure.java.io :as io]
   [polls.middleware :as middleware]
   [ring.util.response]
   [ring.util.http-response :as response]))

(defn home-page [request]
  (layout/render request "questions.html" {:questions (db/get-questions)}))

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/about" {:get about-page}]])
