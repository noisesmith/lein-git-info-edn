(ns leiningen.git-info-edn
  (:require [clojure.java.io :as io]
            [clojure.java.shell :as sh]
            [clojure.string :as string]
            [clojure.pprint :as pprint])
  (:import (java.time Instant)
           (java.io StringWriter)))

(defn generate-info-str
  []
  (let [format-builder (StringWriter.)]
    (pprint/pprint {:hash (string/trim (:out (sh/sh "git" "rev-parse" "HEAD")))
                    :status (string/trim (:out (sh/sh "git" "status")))
                    ;; inserts a format placeholder into the output
                    :date  '%s}
                    format-builder)
    ;; this format approach is needed in order to support older clojure that doesn't generate #inst
    (format (str format-builder)
            (str "#inst \"" (Instant/now) "\""))))

(defn git-info-edn
  "adds git info to the resources directory"
  [& _]
  (spit "resources/deploy-info.edn"
        (generate-info-str))
  true)
