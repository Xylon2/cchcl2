(ns polls.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[polls started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[polls has shut down successfully]=-"))
   :middleware identity})
