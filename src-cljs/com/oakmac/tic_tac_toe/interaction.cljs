(ns com.oakmac.tic-tac-toe.interaction
  (:require
    [com.oakmac.tic-tac-toe.state :refer [initial-state state]]
    [com.oakmac.tic-tac-toe.game-logic :as game-logic]
    [com.oakmac.tic-tac-toe.html :as html]
    [goog.dom :as gdom]
    [goog.functions :as gfunctions]
    [oops.core :refer [ocall oget oset!]]))

(defn set-app-html!
  [html-str]
  (let [el (gdom/getElement "appContainer")]
    (oset! el "innerHTML" html-str)))

(defn render-ui! [_ _kwd _prev-state new-state]
  (set-app-html! (html/TicTacToe new-state)))

(defn turn->square [t]
  (case t
    "O" :o
    "X" :x
    :n))

(defn click-square! [row-idx col-idx]
  (let [current-state @state
        current-turn (:turn current-state)
        next-turn (if (= current-turn "X") "O" "X")
        square-empty? (= :n (get-in current-state [:board row-idx col-idx]))
        game-over? (not= (:result current-state) :unfinished)]
    (when (and square-empty? (not game-over?))
      (swap! state (fn [state]
                     (let [current-board (:board state)
                           new-board (assoc-in current-board [row-idx col-idx] (turn->square current-turn))
                           new-result (game-logic/game-status new-board)]
                       (-> state
                         (assoc :board new-board)
                         (assoc :turn next-turn)
                         (assoc :result new-result))))))))

(defn click-square-el [el]
  (let [row-idx (int (oget el "dataset.rowIdx"))
        col-idx (int (oget el "dataset.colIdx"))]
    (click-square! row-idx col-idx)))

(defn click-app-container [js-evt]
  (let [target-el (oget js-evt "target")
        square? (ocall (oget target-el "classList") "contains" "square")
        new-game-btn? (= (oget target-el "id") "newGameBtn")]
    (cond
      square? (click-square-el target-el)
      new-game-btn? (reset! state initial-state)
      :else nil)))

;; -----------------------------------------------------------------------------
;; Public API

(defn trigger-render! []
  (swap! state identity))

(def init-dom-events!
  (gfunctions/once
    (fn []
      (ocall (gdom/getElement "appContainer") "addEventListener" "click" click-app-container))))

(def init-watchers!
  (gfunctions/once
    (fn []
      (add-watch state :render-ui render-ui!))))
