(ns butler.commands.status)

(def api-root-url "https://api.github.com/")
(def api-repos-url  "repos")
(def repository-owner "anthonyray")
(def repository-name "anthonyray.github.io")
(def repository-url
  (str api-root-url "/" api-repos-url "/" repository-owner "/" repository-name))

(defn run
  [& args]
  (slurp repository-url)
  0)
