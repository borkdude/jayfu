(ns jayfu.main
  (:require [sci.core :as sci])
  ;; Needed for standalone jar to invoke with java
  (:gen-class))

(defn -main [& args]
  (prn (sci/eval-string (second args))))
