(ns leiningen.git-info-edn
  (:require [clojure.java.shell :as sh]
            [clojure.string :as string]
            [clojure.pprint :as pprint])
  (:import (java.time Instant)))

(def date-emitter
  "needed in order to support #inst output in older clojure versions"
  (reify Object
    (toString [this]
      (str "#inst \"" (Instant/now) "\""))))

(defmethod print-method (class date-emitter) [x p]
  (.write p (str x)))

(defn generate-info-str
  []
  (with-out-str
   (pprint/pprint {:hash (string/trim (:out (sh/sh "git" "rev-parse" "HEAD")))
                   :status (string/trim (:out (sh/sh "git" "status")))
                   :date date-emitter})))

(defn git-info-edn
  "adds git info to the resources directory"
  [& _]
  (spit "resources/deploy-info.edn"
        (generate-info-str))
  true)
