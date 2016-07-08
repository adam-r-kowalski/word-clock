(ns word-clock.util
  (:require [cljs-time.core :as time]))

(def rows
  ["itdisptentwenty-"
   "fivejhalfquarter"
   "pastilntwoneight"
   "threelevensixten"
   "fourfivesevenine"
   "twelvelatminfthe"
   "midnightmorningj"
   "afternoonevening"])

(defn w-range [r start end]
  (for [c (range start (inc end))]
    [r c]))

(def words
  [{:word :it
    :coords (w-range 0 0 1)}
   {:word :is
    :coords (w-range 0 3 4)}
   {:word :ten'
    :coords (w-range 0 6 8)}
   {:word :twenty'
    :coords (w-range 0 9 14)}
   {:word :twenty-five'
    :coords (concat (w-range 0 9 15) (w-range 1 0 3))}
   {:word :five'
    :coords (w-range 1 0 3)}
   {:word :a
    :coords [[1 6]]}
   {:word :half
    :coords (w-range 1 5 8)}
   {:word :quarter
    :coords (w-range 1 9 15)}
   {:word :past
    :coords (w-range 2 0 3)}
   {:word :til
    :coords (w-range 2 3 5)}
   {:word :two
    :coords (w-range 2 7 9)}
   {:word :one
    :coords (w-range 2 9 11)}
   {:word :eight
    :coords (w-range 2 10 14)}
   {:word :three
    :coords (w-range 3 0 4)}
   {:word :eleven
    :coords (w-range 3 4 9)}
   {:word :six
    :coords (w-range 3 10 12)}
   {:word :ten
    :coords (w-range 3 13 15)}
   {:word :four
    :coords (w-range 4 0 3)}
   {:word :five
    :coords (w-range 4 4 7)}
   {:word :seven
    :coords (w-range 4 8 12)}
   {:word :nine
    :coords (w-range 4 12 15)}
   {:word :twelve
    :coords (w-range 5 0 5)}
   {:word :in
    :coords (w-range 5 10 11)}
   {:word :the
    :coords (w-range 5 13 15)}
   {:word :midnight
    :coords (w-range 6 0 7)}
   {:word :morning
    :coords (w-range 6 8 14)}
   {:word :afternoon
    :coords (w-range 7 0 8)}
   {:word :noon
    :coords (w-range 7 5 8)}
   {:word :evening
    :coords (w-range 7 9 15)}])

(defn ->coords [coll]
  (reduce
   (fn [acc val]
     (if (some #{(:word val)} coll)
       (concat acc (:coords val))
       acc))
   []
   words))

(defn coord? [coords r c]
  (<= 1 (count (some #{[r c]} coords))))

(defn hour->key [h m]
  (let [prefix (cond
                 (or (<= 58 m) (>= 2 m)) nil
                 (<= m 32) :past
                 :else :til)
        h (if (<= m 32) h (inc h))]
    [prefix
     (cond
       (= 0 h) :midnight
       (or (= 1 h) (= 13 h)) :one
       (or (= 2 h) (= 14 h)) :two
       (or (= 3 h) (= 15 h)) :three
       (or (= 4 h) (= 16 h)) :four
       (or (= 5 h) (= 17 h)) :five
       (or (= 6 h) (= 18 h)) :six
       (or (= 7 h) (= 19 h)) :seven
       (or (= 8 h) (= 20 h)) :eight
       (or (= 9 h) (= 21 h)) :nine
       (or (= 10 h) (= 22 h)) :ten
       (or (= 11 h) (= 23 h)) :eleven
       :else nil)]))

(defn between [small large x]
  (and (<= small x) (>= large x)))

(defn minute->keys [m]
  (cond
    (or (between 3 7 m) (between 53 57 m)) [:five']
    (or (between 8 12 m) (between 48 52 m)) [:ten']
    (or (between 13 17 m) (between 43 47 m)) [:a :quarter]
    (or (between 18 22 m) (between 38 42 m)) [:twenty']
    (or (between 23 27 m) (between 33 37 m)) [:twenty-five']
    (between 28 32 m) [:half]
    :else []))

(defn time-of-day->keys [h m]
  (let [h (if (>= 32 m) h (inc h))]
    (cond
      (= 0 h) []
      (>= 11 h) [:in :the :morning]
      (= 12 h) [:noon]
      (>= 18 h) [:in :the :afternoon]
      (>= 23 h) [:in :the :evening]
      :else [:midnight])))

(defn time->coords [now]
  (let [h (time/hour now)
        m (time/minute now)]
    (->coords (concat [:it :is]
                      (minute->keys m)
                      (hour->key h m)
                      (time-of-day->keys h m)))))
