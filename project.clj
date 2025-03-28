(defproject new-clojure-project "0.1.0-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.logging "1.3.0"]]
  :pedantic? :abort
  :global-vars {*warn-on-reflection* true}

  :main ^:skip-aot kata
  :target-path "target/%s"
  :javac-options ["--release" "11"]

  :profiles {:uberjar {:aot :all
                       :uberjar-name "kata.jar"}})
