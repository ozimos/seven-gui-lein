(ns seven-guis.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as d]
   [seven-guis.challenges.challenge1 :as chlg1]
   [seven-guis.challenges.challenge2 :as chlg2]
   [seven-guis.challenges.challenge3 :as chlg3]
   [seven-guis.challenges.challenge4 :as chlg4]
   [seven-guis.challenges.challenge5 :as chlg5]
   ))

;; -------------------------
;; Views
;; 
(defn caption []
  [:div {:style {:width "100%" :text-align "center"}} [:h2 "Welcome to the 7 GUIs challenge"]])


(defn home-page []
  [:div.mb-40
   [caption]
   [chlg1/ui]
   [chlg2/ui]
   [chlg3/ui]
   [chlg4/ui]
   [chlg5/ui]
   ])

;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))

(comment
  
  (require '[clojure.core.protocols :refer [Datafiable]]
            '[portal.web :as p])
  (p/open)
  (add-tap #'p/submit)
  )
