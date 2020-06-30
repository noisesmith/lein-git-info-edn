(ns leiningen.git-info-edn
  (:require [clojure.java.shell :as sh]
            [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.pprint :as pprint])
  (:import (java.time Instant)))

(def date-emitter
  "needed in order to support #inst output in Clojure 1.10.0"
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
  "adds git info to the resources directory, entry point for the leiningen plugin"
  [& _]
  (let [output-file "resources/deploy-info.edn"]
    (io/make-parents output-file)
    (spit output-file (generate-info-str)))
  true)

(defn -main
  "entry point for the tools.deps plugin"
  [& source-paths]
  (git-info-edn)
  (System/exit 0))
