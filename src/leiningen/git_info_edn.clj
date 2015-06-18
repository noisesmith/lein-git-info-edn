(ns leiningen.git-info-edn
  (:require [clojure.java.shell :as sh]))

(defn git-info-edn
  "I don't do a lot."
  [& _]
  (spit "resources/deploy-info.edn"
        (pr-str {:hash (:out (sh/sh "git" "rev-parse" "HEAD"))
                 :status (:out (sh/sh "git" "status"))})))
