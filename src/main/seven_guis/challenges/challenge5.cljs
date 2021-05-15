(ns seven-guis.challenges.challenge5
  (:require [reagent.core :as r]
            [seven-guis.challenges.shared :as shd]))


(defn handle-click [_]
  (js/alert "Hi"))

(defn crud-button [title on-click]
  [:input.rounded-xl.px-4.bg-white.h-6 {:value title :on-click on-click :type "button"}])


(defn labelled-input [label on-click class]
  [:div.flex.my-auto.mb-3.justify-between {:on-click on-click :class class} 
   [:p.m-2 label] [:input {:type "text"}]])

(defn crud []
  [:div.font-bold.p-4.bg-gray-300
   [labelled-input "Filter prefix:" handle-click "w-1/2"]
   [:div.flex
    [:div.px-4.bg-white.border-solid.mr-auto {:class "w-1/2"}
     [:p "Emil, Hans"]
     [:p "Emil, Hans"] [:p "Emil, Hans"]]
    [:div [labelled-input "Name:"] [labelled-input "Surname:"]]]
   [:div.flex.justify-between.py-4 {:class "w-1/2"}
    [crud-button "Create" handle-click]
    [crud-button "Update" handle-click]
    [crud-button "Delete" handle-click]]])

(defn ui []
  [:div.px-40
   [shd/challenge-section 5 "CRUD"]
   [crud]])

(comment
  
  (js/alert "Hello There")
  
  "")
