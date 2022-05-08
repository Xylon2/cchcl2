(ns polls.routes.home
  (:require
   [polls.layout :as layout]
   [polls.db.core :as db]
   [polls.auth :as auth]
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

(defn results-page [request]
  (let [qid (Integer/parseInt ((request :query-params) "id"))]
    (layout/render request "results.html"
                   {:choices (db/get-choices
                              {:question qid})})))

(defn save-vote [request]
  (let [choice (-> request :params :choice Integer/parseInt)]
    (db/save-vote {:id choice})
    (response/found (str "/results?id=" ((request :query-params) "id"))))
  )

(defn about-page [request]
  (layout/render request "about.html"))

(defn home-routes []
  [ "" 
   {:middleware [middleware/wrap-csrf
                 middleware/wrap-formats]}
   ["/" {:get home-page}]
   ["/detail" {:get detail-page}]
   ["/vote"    {:post save-vote}]
   ["/results" {:get results-page}]
   ["/about" {:get about-page}]])
