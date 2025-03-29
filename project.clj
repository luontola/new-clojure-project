(defproject new-clojure-project "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.logging "1.3.0"]]
  :main ^:skip-aot kata
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :uberjar-name "kata.jar"}})
