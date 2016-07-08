(require '[figwheel-sidecar.repl-api :as ra])

(defn start []
  (ra/start-figwheel!
    {:figwheel-options {}
     :build-ids ["dev"]
     :all-builds
     [{:id "dev"
       :figwheel true
       :source-paths ["src"]
       :compiler {:main 'word-clock.core
                  :asset-path "js"
                  :output-to "resources/public/js/main.js"
                  :output-dir "resources/public/js"
                  :verbose true}}]})
  (ra/cljs-repl "dev"))

(defn stop [] (ra/stop-figwheel!))
