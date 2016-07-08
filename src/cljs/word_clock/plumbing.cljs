(ns word-clock.plumbing
  (:require [reagent.core :as r]
            [devtools.core :as devtools]))

(defonce on-page-load
  (do (devtools/install! [:custom-formatters :sanity-hints])
      true))

(defn render [component]
  (r/render-component
   component
   (js/document.getElementById "app")))
