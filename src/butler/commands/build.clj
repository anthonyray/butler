(ns butler.commands.build
  (:require [selmer.parser :as templating])
  (:require [markdown.core :as markdown-parser]))

(def articles-path "./articles")
(def template-path "./templates/article.html")

(defn read-template
  []
  (slurp (clojure.java.io/file template-path)))

(defn extract-article-from-file
  "Extract article data structure from article file"
  [article-file]
  {
   :name (first (clojure.string/split (.getName article-file) #"\."))
   :last-modified (.lastModified article-file)
   :raw-text (slurp article-file)
   })

(defn enrich-article-from-markdown
  "Enriches the article data structure with data extracted from markdown"
  [article]
  (let [markdown-article (markdown-parser/md-to-html-string-with-meta (:raw-text article))]
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
    :html (templating/render
            (read-template)
            article))
  )

(defn write-article                                         ;; Side effect happens here.
  [article]
  (println (str  "WRITING :" (:name article) ".html"))
  (spit (str (:name article) ".html") (:html article))
  article
  )

(defn list-files
  "Filters articles which extension is .md"
  []
  (filter
    #(.isFile %)
    (file-seq (clojure.java.io/file articles-path))
    ))

(defn run
  [& args]
  (let [articles (map
                   #((comp
                       render-article
                       enrich-article-from-markdown
                       extract-article-from-file) %)
                   (list-files))]
    (doseq [article articles]
      (write-article article))
    0)                           ;; where the side effect happens.
  )
