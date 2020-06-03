(ns pushgp-similarity.core
  (:require [consimilo.core :as consimilo]
            [clojure.java.io :as io]
            [clojure.edn :as edn])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(defn individual->feature
  [individual]
  {:id (:uuid individual)
   :generation (:generation individual)
   :features (:errors individual)
   :total-error (:total-error individual)})

(defn edn-file->features
  [filename]
  (with-open [reader (io/reader filename)]
    (doall
     (map
      #(->> %
            (edn/read-string {:default (fn [t v] v)})
            (individual->feature))
      (rest (line-seq reader))))))

(defn edn-file->forest
  [filename]
  (consimilo/add-all-to-forest (edn-file->features filename)))