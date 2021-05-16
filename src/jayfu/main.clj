(ns jayfu.main
  (:require [clojure.data.json :as json]
            [clojure.tools.cli :as cli]
            [sci.core :as sci])
  ;; Needed for standalone jar to invoke with java
  (:gen-class))

(def cli-options
  [["-f" "--func FUNCTION" "The function applied to JSON from stdin"
    :default "identity"]
   ["-k" "--key-fn KEY-FN" "The function applied to keywords"
    :default "identity"]
   ["-h" "--help"]])

(def eval-fn

  ;; load-string is not GraalVM-compatible:
  ;; Caused by: com.oracle.svm.core.jdk.UnsupportedFeatureError: Defining classes from new bytecodes run time.
  ;; load-string

  ;; works with GraalVM
  sci/eval-string
  )

(defn -main [& args]
  (let [parsed (cli/parse-opts args cli-options)
        {:keys [:func
                :key-fn
                :help]}
        (:options parsed)]
    (if help
      (do
        (println "Usage:")
        ;; Tools.cli provides a :summary key that can be used for printing:
        (println (:summary parsed)))
      (let [func (if func
                   (eval-fn func)
                   identity)
            key-fn (if key-fn
                     (eval-fn key-fn)
                     identity)]
        (prn (func
              ;; *in* represents a reader from stdin in Clojure
              ;; json/read can handle readers
              ;; :key-fn transforms JSON string keys
              (json/read *in* :key-fn key-fn)))))))
