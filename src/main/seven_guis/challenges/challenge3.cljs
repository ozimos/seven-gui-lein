(ns seven-guis.challenges.challenge3
  (:require [reagent.core :as r]
            [clojure.string :as cstr]
            [cljs-time.core :as timec]
            [cljs-time.format :as formatc]
            [seven-guis.challenges.shared :as shd]))

(def flight-select (r/atom "start"))
(def date-start (r/atom {}))
(def date-return (r/atom {}))

(def dformatter (formatc/formatter "yyyy.MM.dd"))
(defn parse-date-string [v]
  (formatc/parse dformatter v))

(def input-attr {:style {:border-radius "5px"
                                      :width "500px"
                                      :margin "10px 20px"
                                      :padding "0 10px"
                                      :font-size "20px"
                                      :height "60px"}})


(def select-input-attr (assoc input-attr 
                              :name "booking-select" 
                              :on-change #(reset! flight-select (-> % .-target .-value))))

(defn select-input []
  [:select (assoc select-input-attr :value @flight-select)
   [:option {:value "start"} "One Way Flight"]
   [:option {:value "return"} "Return Flight"]])

(defn valid-date? [d] (if (try (timec/after? (parse-date-string d) (timec/today))
                               (catch :default _ false))
                        {:date d :valid true} {:date d}))
(defn cs [& names]
  (cstr/join " " (filter identity names)))

(def date-input-attr (assoc input-attr :type "text"))
(defn date-input 
  ([store] (date-input store nil))
  ([store toggle]
                  (let [{:keys [date valid]} @store]
                    [:input (assoc date-input-attr :value date
                                   :class (cs (when-not valid "ch3"))
                                   :disabled (and toggle (= "start" @flight-select))
                                   :on-change #(reset! store (valid-date? (-> % .-target .-value))))])))

(defn booking-message []
  (if (= :start @flight-select) (str "You have booked a one-way flight on " (:date @date-start))
      (str "You have booked a return flight with start " (:date @date-start) " and return " (:date @date-return))))
(defn booking-handler [evt]
  (.preventDefault evt)
  (js/alert (booking-message) ))

(def button-input-attr (assoc input-attr :type "button" :value "Book"))
(defn button-input []
  [:input (assoc button-input-attr 
                 :on-click booking-handler
                 :class "hover:cursor-pointer hover:bg-blue-500"
                 :disabled (or (try (timec/after?
                                     (parse-date-string (:date @date-start))
                                     (parse-date-string (:date @date-return)))
                                    (catch :default _ false))
                               (not-every? :valid [@date-start @date-return]))) ])

(def booking-comp-attr (assoc-in shd/flex-attr [:style :flex-direction] "column") )
(defn booking-component []
  (let [current-date (formatc/unparse dformatter (timec/today))
        date-init {:date current-date :valid true}]
    (reset! date-start date-init)
    (reset! date-return date-init))
  [:div booking-comp-attr
   [:label {:for "booking-select"} "Book your flight"]
   [select-input] 
   [date-input date-start] 
   [date-input date-return true] 
   [button-input] 
   ])

(defn ui []
  [:div
   [shd/challenge-section 3 "Flight Booker"]
   [booking-component]])

