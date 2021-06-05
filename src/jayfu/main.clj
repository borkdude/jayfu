(ns jayfu.main
  (:require [asami.core :as d]
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

(defn -main [& args]
  (let [db-uri "asami:local://dbname"
        _ (d/create-database db-uri)
        conn (d/connect db-uri)
        first-movies [{:movie/title "Explorers"
                       :movie/genre "adventure/comedy/family"
                       :movie/release-year 1985}
                      {:movie/title "Demolition Man"
                       :movie/genre "action/sci-fi/thriller"
                       :movie/release-year 1993}
                      {:movie/title "Johnny Mnemonic"
                       :movie/genre "cyber-punk/action"
                       :movie/release-year 1995}
                      {:movie/title "Toy Story"
                       :movie/genre "animation/adventure"
                       :movie/release-year 1995}]
        _ @(d/transact conn {:tx-data first-movies})
        db (d/db conn)
        res (d/q '[:find ?movie-title
                   :where [?m :movie/title ?movie-title]] db)]
    (prn res)
    (shutdown-agents)))
