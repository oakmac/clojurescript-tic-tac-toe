(ns com.oakmac.tic-tac-toe.core
  (:require
    [com.oakmac.tic-tac-toe.interaction :refer [init-dom-events! init-watchers! trigger-render!]]
    [goog.functions :as gfunctions]
    [oops.core :refer [ocall]]))

(def init!
  (gfunctions/once
    (fn []
      (println "Initializing Tic-Tac-Toe ❌⭕❌")
      (init-dom-events!)
      (init-watchers!)
      (trigger-render!))))

(ocall js/window "addEventListener" "load" init!)
