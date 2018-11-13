(ns butler.commands.status
  (:require [cheshire.core :refer :all]))

(def api-root-url "https://api.github.com")
(def api-repos-url  "repos")
(def api-commits-url  "commits")
(def repository-owner "anthonyray")
(def repository-name "anthonyray.github.io")
(def repository-url
  (str api-root-url "/" api-repos-url "/" repository-owner "/" repository-name))
(def repository-commits
  (str repository-url "/" api-commits-url))


(defn generate-report
  [status]
  (str "* Last push was " (:update_date status) "\n"
       "* with message : " (:message status) "\n"
       "* Commit SHA : " (:sha status) "\n"
       "* Up on https://anthonyray.github.io"))

(defn run
  [& args]
  (let [api-call (parse-string (slurp repository-url) true)
        first-commit (:commit (first (parse-string (slurp repository-commits) true)))
        status {
                :update_date (:updated_at api-call)
                :message (:message first-commit)
                :sha (get-in first-commit [:tree :sha])
                :by (get-in first-commit [:author :name])}]
    (println (generate-report status)))
  0)

