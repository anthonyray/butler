(ns butler.core
  (:require [butler.commands.status :as status])
  (:require [butler.commands.missing :as missing])
  (:gen-class))

(def routing
  {:status status/run
   :version (fn [] 0)
   :build (fn [] 0)
   :deploy (fn [] 0)})

(defn run
  "Executes a subcommand. The subcommand has to return the exit code ? "
  [[subcommand & args]]
  (
    (get
      routing
      (keyword (str subcommand))
      missing/run)

    args
    ))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (System/exit (run args)))
