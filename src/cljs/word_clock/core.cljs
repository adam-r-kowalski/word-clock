(ns word-clock.core
  (:require [word-clock.plumbing :refer [render]]
            [word-clock.clock :refer [clock]]
            [cljs-time.local :refer [local-now]]
            [cljs-time.core :refer [today-at]]
            [reagent.core :as r]))

(def now (r/atom (local-now)))

(defonce timer
  (do (js/setInterval #(reset! now (local-now)) 1000)
      true))

(render [clock now])
