(ns butler.commands.version)

(defn run
  [& args]
  (println
    (System/getProperty "butler.version"))
  0)

