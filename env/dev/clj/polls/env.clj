(ns polls.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [polls.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[polls started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[polls has shut down successfully]=-"))
   :middleware wrap-dev})
