(defproject word-clock "0.1.0-SNAPSHOT"
 :description "My project description!"
 :dependencies [[org.clojure/clojure "1.8.0"]
                [org.clojure/clojurescript "1.8.40"]
                [org.clojure/core.async "0.2.374"]

                [binaryage/devtools "0.7.2"]
                [reagent "0.6.0-rc"]
                [com.andrewmcveigh/cljs-time "0.4.0"]]

 :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                 [figwheel-sidecar "0.5.4-5" :scope "test"]
                                 [binaryage/devtools "0.7.2"]]

                  :source-paths ["dev"]}}

 :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
