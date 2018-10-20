(ns butler.core
  (:gen-class))

(defn status
  [args]
  (println " * Status command will be invoked")
  (println " * 🎩 Done !")
  0)

(def routing
  {:status status
   :version (fn [] 0)
   :build (fn [] 0)
   :deploy (fn [] 0)})


(defn missing-subcommand
  [args]
  "Prints the usage message"
  (println
    "TODO")
  1
  )


(defn run
  "Executes a subcommand. The subcommand has to return the exit code ? "
  [[subcommand & args]]
  (
    (get
      routing
      (keyword (str subcommand))
      missing-subcommand)

    args
    ))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (System/exit (run args)))
