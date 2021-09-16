;; see https://ask.clojure.org/index.php/10905/control-transient-deps-that-compiled-assembled-into-uberjar?show=10913#c10913
(require 'clojure.tools.deps.alpha.util.s3-transporter)

(ns build
  (:refer-clojure :exclude [compile])
  (:require
   [clojure.tools.build.api :as b]))

(def class-dir "target/classes")
(def version "0.0.1")

(def uber-basis
  (b/create-basis {:project "deps.edn"
                   :aliases [:native-deps]}))

(defn uber [_]
  (println "Compiling sources sources.")
  (b/compile-clj {:basis uber-basis
                  :src-dirs ["src"]
                  :class-dir class-dir
                  :ns-compile '[jayfu.main]})
  (println "Building uberjar.")
  (b/uber {:class-dir class-dir
           :uber-file "jayfu.jar"
           :basis uber-basis
           :main 'jayfu.main}))
