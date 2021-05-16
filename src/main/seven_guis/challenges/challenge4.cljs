(ns seven-guis.challenges.challenge4
  (:require [reagent.core :as r]
            [seven-guis.challenges.shared :as shd]))

(def seconds-elapsed (r/atom 0))
(def duration (r/atom 20))
(def timer-fn (atom nil))

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

(defn slider-change-handler [e] (let [v (js/parseInt (.. e -target -value))]
                                             (reset! duration v)
                                             (when (and (nil? @timer-fn) (> v @seconds-elapsed)) (start-timer))))

(defn slider []
  [:input.w-80 {:type "range"
           :min 0 :max 60
           :on-change slider-change-handler :value @duration}])

(defn progress []
  [:div.border-solid.border-black.border-2.w-80.h-5.rounded-lg
   [:div.h-4.rounded-lg.bg-red-500 {:style {:width (str (-> (/ @seconds-elapsed @duration) (* 100) (.toFixed 2) (min 100)) "%")}}]])



(defn countdown-component []
  [:div
   [:p {:style {:white-space "nowrap"}} "Elapsed Time:"]
   [:p {:style {:font-size "34px"}} (str @seconds-elapsed " s")]])

(defn timer-slider []
  (r/with-let [_ start-timer]
    [:div.px-72 
     [:div.flex.justify-between.mb-3 [countdown-component] [progress]]
     [:div.flex.justify-between.mb-3  "Duration" [slider]]
     [:div.flex.justify-center.mb-3
      [:input.w-full.text-xl.rounded-lg.py-4 
       {:class "hover:cursor-pointer hover:bg-blue-500" 
        :type "button" 
        :value "Reset" 
        :on-click (fn [_] (reset! seconds-elapsed 0) (stop-timer) (start-timer))}]]]
    (finally stop-timer)))

(defn ui []
  [:div
   [shd/challenge-section 4 "Timer"]
   [:f> timer-slider]])

(comment)
