(ns seven-guis.challenges.challenge4
  (:require [reagent.core :as r]
            [seven-guis.challenges.shared :as shd]))

(def seconds-elapsed (r/atom 0))
(def duration (r/atom 20))
(def timer-fn (atom nil))

(def input-attr {:style {:border-radius "5px"
                         :width "400px"
                         :margin "10px 20px"
                         :padding "0 10px"
                         :font-size "20px"
                         :height "60px"}})


(defn stop-timer []
  (when-let [timer-id @timer-fn]
    (js/clearTimeout timer-id)
    (reset! timer-fn nil)))

(defn tick []
  (if (> @duration @seconds-elapsed)
    (do
      (swap! seconds-elapsed inc)
      (reset! timer-fn (js/setTimeout tick 1000)))
    (stop-timer)))

(defn start-timer []
  (reset! timer-fn (js/setTimeout tick 1000)))


(def slider-attr (assoc input-attr :type "range" 
                        :min 0 :max 60 
                        :on-change (fn [e] (let [v (js/parseInt (.. e -target -value))]
                                             (reset! duration v)
                                             (when (and (nil? @timer-fn) (> v @seconds-elapsed)) (start-timer)))) ))
(defn slider []
  [:input (assoc slider-attr :value @duration)])

(defn progress []
  [:div {:style {:width "400px" :height "20px" :border "solid"}}
   [:div {:style {:width (str (-> (/ @seconds-elapsed @duration) (* 100) (.toFixed 2) (min 100)) "%") :background-color "red" :height "20px"}}]])


(def button-input-attr (assoc input-attr :type "button" :value "Reset" :on-click (fn [_] (reset! seconds-elapsed 0) (stop-timer) (start-timer))))
(defn button-input []
  [:input button-input-attr])

(def flex-column (merge shd/flex-attr {:style {:flex-direction "column" :margin "auto 10px"}}))


(defn countdown-component []
  [:div flex-column 
   [:p {:style {:white-space "nowrap"}} "Elapsed Time:"]
   [:p {:style {:font-size "34px"}} (str @seconds-elapsed)]]
  )

(defn timer-slider []
  (r/with-let [_ start-timer]
    [:div flex-column
     [:div shd/flex-attr [countdown-component] [progress]]
     [:div shd/flex-attr  "Duration" [slider]]
     [button-input]]
    (finally stop-timer)))

(defn ui []
  [:div
   [shd/challenge-section 4 "Timer"]
   [:f> timer-slider]])

(comment)
