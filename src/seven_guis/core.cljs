(ns seven-guis.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [seven-guis.challenges.challenge1 :as chlg1]
   [seven-guis.challenges.challenge2 :as chlg2]))

;; -------------------------
;; Views
;; 
(defn caption []
  [:div {:style {:width "100%" :text-align "center"}} [:h2 "Welcome to the 7 GUIs challenge"]])


(defn home-page []
  [:div
   [caption]
   [chlg1/ui]
   [chlg2/ui]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
