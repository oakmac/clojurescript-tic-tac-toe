(ns com.oakmac.tic-tac-toe.html
  (:require-macros [hiccups.core :as hiccups :refer [html]])
  (:require [hiccups.runtime :as hiccups]))

(defn BoardSquare [x-or-o row-idx col-idx]
  (let [x-or-o (case x-or-o
                 :x "X"
                 :o "O"
                 "")]
    [:div {:class (str "square " x-or-o)
           :data-row-idx row-idx
           :data-col-idx col-idx}
     x-or-o]))

(defn BoardRow [row-idx [sq1 sq2 sq3]]
  [:div.row
   (BoardSquare sq1 row-idx 0)
   (BoardSquare sq2 row-idx 1)
   (BoardSquare sq3 row-idx 2)])

(defn Board [[row1 row2 row3]]
  [:div.board-container
    [:div.board
      (BoardRow 0 row1)
      (BoardRow 1 row2)
      (BoardRow 2 row3)]])

(defn GameOverBanner [result]
  [:div.banner-wrapper
    [:div
      {:class (case result
                (:x-wins :o-wins) "alert alert-success"
                :tie "alert alert-dark"
                nil)}
      [:h2.alert-heading
       (case result
         :x-wins "X wins!"
         :o-wins "O wins!"
         :tie "Tie game!"
         nil)]
      [:button#newGameBtn.btn.btn-primary "New Game"]]])

(defn PlayerTurnBanner [x-or-o]
  [:div.banner-wrapper.turn-wrapper
    [:span.label "Current turn:"]
    [:span {:class (str "current-turn " x-or-o)} x-or-o]])

(defn PageTitle []
  [:h1.display-3 "Tic Tac Toe"])

(hiccups/defhtml TicTacToe
  [{:keys [turn result board]}]
  [:main.text-center
   (PageTitle)
   (if (= result :unfinished)
     (PlayerTurnBanner turn)
     (GameOverBanner result))
   (Board board)])
