(ns butler.core
  (:require [butler.commands.missing :as missing])
  (:require [butler.commands.status :as status])
  (:require [butler.commands.version :as version])
  (:require [butler.commands.build :as build])
  (:require [butler.commands.deploy :as deploy])
  (:gen-class))

(def routing
  {:status status/run
   :version version/run
   :build build/run
   :deploy deploy/run

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
