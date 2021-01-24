(ns seven-guis.challenges.shared
  (:require [reagent.core :as r]))


(defn challenge-section [n title]
  [:section {:style {:margin "20px 0" :text-align "center"}}
   [:hr]
   [:div (str "Challenge " n ": "  title)]])

(def flex-attr {:style {:display "flex" :justify-content "center" :align-items "center" :margin "10px auto"}})
