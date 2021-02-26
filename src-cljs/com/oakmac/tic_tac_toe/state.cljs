(ns com.oakmac.tic-tac-toe.state
  (:require
    [com.oakmac.tic-tac-toe.game-logic :as game-logic]))

(def initial-state
  {:turn "X"
   :result :unfinished
   :board (game-logic/empty-board)})

(def state (atom initial-state))
