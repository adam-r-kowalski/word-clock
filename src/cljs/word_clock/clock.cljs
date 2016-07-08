(ns word-clock.clock
  (:require [word-clock.util :as u]))

(def styles
  {:clock {:font-family "monospace"
           :font-size 48
           :text-transform "uppercase"
           :display "flex"
           :flex-direction "column"
           :justify-content "center"
           :align-items "center"
           :height "100vh"
           :background-color "hsla(210, 29%, 24%, 1)"}})

(defn color [coords r c]
  {:transition "all 1s ease"
   :color (if (u/coord? coords r c)
            "hsla(192, 15%, 94%, 1)"
            "hsla(184, 6%, 53%, 0.3)")})

(defn column [coords r c letter]
  [:div
   {:style (merge {:margin 5} (color coords r c))}
   letter])

(defn row [coords r letters]
  [:div {:style {:display "flex"}}
   (map-indexed
    (fn [c letter]
      ^{:key (gensym)}
      [column coords r c letter])
    letters)])

(defn clock [now]
  (let [n @now]
    [:div {:style (:clock styles)}
     (map-indexed
      (fn [r letters]
        ^{:key (gensym)}
        [row (u/time->coords n) r letters])
      (map seq u/rows))]))
