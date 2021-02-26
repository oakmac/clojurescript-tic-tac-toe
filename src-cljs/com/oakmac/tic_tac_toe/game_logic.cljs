(ns com.oakmac.tic-tac-toe.game-logic
  (:require
    [clojure.string :as str]))

(defn empty-board []
  [[:n :n :n]
   [:n :n :n]
   [:n :n :n]])

(defn board-str->vec
  [board-str]
  (let [vec-of-strings (str/split board-str ",")
        board-of-strings (mapv #(str/split % "") vec-of-strings)
        board (mapv
                (fn [row]
                  (mapv keyword row))
                board-of-strings)]
    board))

(def o-wins-vertical1
  [[:o :o :x]
   [:x :o :n]
   [:o :o :n]])

(def o-wins-vertical1-str "oox,xon,oon")

(def x-wins-vertical1
  [[:o :o :x]
   [:x :n :x]
   [:o :o :x]])

(def x-wins-vertical1-str "oox,xnx,oox")

(def x-wins-horizontal
  [[:x :x :x]
   [:x :n :o]
   [:o :o :x]])

(def x-wins-horizontal-str "xxx,xno,oox")

(def tie-board
  [[:x :o :x]
   [:x :x :o]
   [:o :x :o]])

(def tie-board-str "xox,xxo,oxo")

(def unfinished-board
  [[:x :o :x]
   [:x :n :o]
   [:o :o :x]])

(def unfinished-board-str "xox,xno,oox")

(assert (= (board-str->vec o-wins-vertical1-str) o-wins-vertical1))
(assert (= (board-str->vec x-wins-vertical1-str) x-wins-vertical1))
(assert (= (board-str->vec x-wins-horizontal-str) x-wins-horizontal))
(assert (= (board-str->vec tie-board-str) tie-board))
(assert (= (board-str->vec unfinished-board-str) unfinished-board))

(defn board-str-valid?
  [board-str])
  ;; TODO: returns true or false if the board string is formatted correctly

(def winning-coords
  #{;; horizontal rows
    #{[0 0] [0 1] [0 2]}
    #{[1 0] [1 1] [1 2]}
    #{[2 0] [2 1] [2 2]}

    ;; vertical column
    #{[0 0] [1 0] [2 0]}
    #{[0 1] [1 1] [2 1]}
    #{[0 2] [1 2] [2 2]}

    ;; diagonals
    #{[0 0] [1 1] [2 2]}
    #{[0 2] [1 1] [2 0]}})

(defn check-coord
  [board coords]
  (let [vals-at-coord (map
                        (fn [coord]
                          (get-in board coord))
                        coords)]

    (cond
      (every? #(= :x %) vals-at-coord)
      :x-wins

      (every? (fn [x] (= :o x)) vals-at-coord)
      :o-wins

      (some (fn [x] (= :n x)) vals-at-coord)
      :unfinished

      :else :full)))

(assert (= :o-wins (check-coord o-wins-vertical1 #{[0 1] [1 1] [2 1]})))
(assert (= :x-wins (check-coord x-wins-horizontal #{[0 0] [0 1] [0 2]})))
(assert (= :full (check-coord tie-board #{[0 1] [1 1] [2 1]})))
(assert (= :unfinished (check-coord unfinished-board #{[0 0] [1 1] [2 2]})))

(defn game-status
  [board]
  (let [all-coords (map
                      (fn [coords]
                        (check-coord board coords))
                      winning-coords)]
    (cond
      (some #(= :o-wins %) all-coords)
      :o-wins

      (some #(= :x-wins %) all-coords)
      :x-wins

      (every? #(= :full %) all-coords)
      :tie

      :else
      :unfinished)))

(assert (game-status o-wins-vertical1) :o-wins)
(assert (game-status x-wins-vertical1) :x-wins)
(assert (game-status x-wins-horizontal) :x-wins)
(assert (game-status tie-board) :tie)
(assert (game-status unfinished-board) :unfinished)

;; -----------------------------------------------------------------
;; Public API

; (defn tic-tac-toe
;   [board-str]
;   (let [board (board-str->vec board-str)
;         result (game-status board)]
;     (case result
;       :o-wins (println "O wins!")
;       :x-wins (println "X wins!")
;       :tie (println "Game over: draw")
;       :unfinished (println "No winner yet. Play on!")
;       nil)))
