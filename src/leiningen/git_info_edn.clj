(ns leiningen.git-info-edn
  (:require [clojure.java.shell :as sh])
  (:import (java.util Date)))

(defn git-info-edn
  "adds git info to the resources directory"
  [& _]
  (spit "resources/deploy-info.edn"
    (pr-str
      {:hash (:out (sh/sh "git" "rev-parse" "HEAD"))
       :status (:out (sh/sh "git" "status"))
       :date (Date.)}))
  true)
