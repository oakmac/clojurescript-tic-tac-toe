(ns com.oakmac.tic-tac-toe.core
  (:require
    [goog.dom :as gdom]
    [goog.functions :as gfunctions]
    [oops.core :refer [ocall oset!]]))

(def init!
  (gfunctions/once
    (fn []
      (println "Initializing Tic-Tac-Toe ❌⭕❌"))))

(ocall js/window "addEventListener" "load" init!)
