(ns seven-guis.challenges.challenge1
  (:require [reagent.core :as r]
            [seven-guis.challenges.shared :as shd]))


(def click-count (r/atom 0))

(defn counting-component []
  [:div shd/flex-attr
   [:p {:style {:font-size "40px" :margin "0 20px"}} @click-count]
   [:input {:type "button" :class "hover:cursor-pointer hover:bg-blue-500" :value "Count" :style {:border-radius "10px" :padding "0 10px" :font-size "20px" :height "60px"}
            :on-click #(swap! click-count inc)}]])

(defn ui []
  [:div
   [shd/challenge-section 1 "Counter"]
   [counting-component]])

