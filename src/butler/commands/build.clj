(ns butler.commands.build
  (:require [selmer.parser :as templating])
  (:require [markdown.core :as markdown-parser])
  (:require [selmer.util :refer [without-escaping]]))

(def articles-path "./articles")
(def template-path "./templates/article.html")
(def output-path "./articles/")

(defn read-template
  []
  (slurp (clojure.java.io/file template-path)))


(defn is-markdown-file?
  [file]
  (and (.isFile file) (= (second (clojure.string/split (.getName file) #"\.")) "md")))


(defn read-article-files
  "Filters articles which extension is .md"
  []
  (filter
    is-markdown-file?
    (file-seq (clojure.java.io/file articles-path))
    ))


(defn extract-article-from-file
  "Extract article data structure from article file"
  [article-file]
  {
   :name (first (clojure.string/split (.getName article-file) #"\."))
   :last-modified (.lastModified article-file)
   :raw-text (slurp article-file)
   })


(defn style-headings                                        ;; TODO: Move this function to a central place where styling is decided.
  [text state]
  [(clojure.string/replace text #"<h[1-5]" #(str %1 " class=\"mt-5\"")) state] ;; TODO : the class doesn't reflect the hierarchy of the headings.
  )


(defn enrich-article-from-markdown
  "Enriches the article data structure with data extracted from markdown"
  [article]
  (let [markdown-article (markdown-parser/md-to-html-string-with-meta
                           (clojure.string/replace (:raw-text article) #"#" "##")
                           :custom-transformers [style-headings])] ;; We customize the behaviour of our parser
    (assoc article
      :title (first (get-in markdown-article [:metadata :title]))
      :last-update-date (first (get-in markdown-article [:metadata :last-update-date]))
      :tags (clojure.string/split (first (get-in markdown-article [:metadata :tags])) #" ")
      :html-content (get markdown-article :html))                   ;; TODO : heading classes won't be correct ...
    )
  )


(defn render-article
  "Given a article datastructure, returns the HTML rendered article."
  [article]
  (assoc
    article
    :html (without-escaping (templating/render
                              (read-template)
                              article)))
  )


(defn generate-report
  [articles]
  (let [number-of-generated-articles (count articles)
        number-of-ignored-articles 0]
    (str
      "* Reading the content of folder: " articles-path "\n"
      "* Found " number-of-generated-articles " entrie(s) : \n"
      (reduce (fn [acc s] (str acc "*\t- " s "\n")) "" (map #(:name %) articles))
      "* Generated " number-of-generated-articles " html pages using template: " template-path "\n"
      "* Using stylesheet : ./styles/main.css\n"
      "* Ignored " number-of-ignored-articles " entries\n"
      "* 🎩 Done !"
      )
    )
  )


;; Side effect functions
(defn write-article
  [article]
  (spit (str output-path (:name article) ".html") (:html article))
  article
  )


;; TODO : Error handling!

(defn run
  [& args]
  (let [articles (map
                   #((comp
                       render-article
                       enrich-article-from-markdown
                       extract-article-from-file) %)
                   (read-article-files))]  ;; where the side effect happens.
    (doseq [article articles]
      (write-article article))   ;; where the side effect happens.
    (println (generate-report articles))
    0)                                                      ;; Return code
  )
