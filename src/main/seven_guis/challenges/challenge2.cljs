(ns seven-guis.challenges.challenge2
  (:require [reagent.core :as r]
            [seven-guis.challenges.shared :as shd]))

(def temp-vals (r/atom {}))

(defn cel->fah [c] {:c c :f (+ (* c 1.8) 32)})

(defn fah->cel [f] {:c (* (- f 32) 0.5556) :f f})

(defn make-change-handler [conv-fn] 
  (fn [evt]
    (.preventDefault evt)
    (let [input (js/parseFloat (-> evt .-target .-value))]
     (->> (if (js/Number.isNaN input) {:c "" :f ""}
              (conv-fn input)) (reset! temp-vals)))))

(defn temp-input [key conv-fn]
  [:input {:type "text" :style {:border-radius "5px"
                                :width "100px"
                                :margin "0 20px"
                                :padding "0 10px"
                                :font-size "20px"
                                :height "60px"}
           :value (get @temp-vals key "")
           :on-change (make-change-handler conv-fn)}])

(defn converter-component []
  [:div shd/flex-attr 
   [:span [temp-input :c cel->fah]
    "Celsius = "]
   [:span [temp-input :f fah->cel]
    "Fahrenheit"]])

(defn ui []
  [:div
   [shd/challenge-section 2 "Temperature Converter"]
   [converter-component]])


(comment
 
  )