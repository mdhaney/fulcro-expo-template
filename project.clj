(defproject fulcro-expo "0.1.0-SNAPSHOT"

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "0.4.474"]
                 [thheller/shadow-cljs "2.8.39"]
                 [com.fulcrologic/fulcro "3.0.1"]
                 [binaryage/oops "0.7.0"]]

  :source-paths ["src/main"]

  :profiles {:cljs {:source-paths ["src/main"]
                    :dependencies [[binaryage/devtools "0.9.10"]
                                   [com.andrewmcveigh/cljs-time "0.5.2"]]}})
