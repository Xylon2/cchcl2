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

(defn detail-page [request]
  (let [qid (Integer/parseInt ((request :query-params) "id"))]
    (layout/render request "detail.html"
                   {:choices (db/get-choices
                              {:question qid})
                    :question (db/question-name {:id qid})})))

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/detail" {:get detail-page}]
   ["/about" {:get about-page}]])
