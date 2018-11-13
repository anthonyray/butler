(defproject butler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [selmer "1.12.2"]
                 [markdown-clj "1.0.4"]
                 [cheshire "5.8.1"]]
  :main butler.core
  :aot [butler.core]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
