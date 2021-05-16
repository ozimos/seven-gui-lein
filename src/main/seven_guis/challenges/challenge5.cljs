(ns seven-guis.challenges.challenge5
  (:require [reagent.core :as r]
            [cljs.spec.alpha :as s]
            [seven-guis.challenges.shared :as shd]))

(def person (r/atom {}))

(s/def ::name (s/and string? not-empty))
(s/def ::surname (s/and string? not-empty))


(s/def ::person (s/keys :req-un [::name ::surname]))

(def person-list (r/atom {:prefix "" :people [] :selected nil}))

(def selected (r/atom nil))

(defn handle-click [_]
  (js/alert "Hi"))

(defn crud-button [title on-click]
  (fn crud-button-inner [disabled? person-info]
    (let [handle-crud-click (fn [_] (on-click person-info))
          class (when-not disabled? "hover:cursor-pointer hover:bg-white")]
      [:input.rounded-xl.px-4.h-6
       {:value title
        :on-click handle-crud-click
        :class class
        :type "button"
        :disabled disabled?}])))


(defn labelled-input [label name on-change class]
  [:div.flex.my-auto.mb-3.justify-between {:on-change on-change :class class} 
   [:p.my-2 label] [:input {:type "text" :name name :class "w-3/5"}]])


(defn handle-input-change [e] (swap! person assoc (keyword (.. e -target -name)) (.trim (.. e -target -value))) )

(defn handle-prefix-change [e] (swap! person-list assoc :prefix (.trim (.. e -target -value))) )

(defn create-button []
  (let [create-button-inner (crud-button "Create" (fn [v] (swap! person-list update-in [:people] conj v)))]
    (fn []
      (let [person-info @person]
        [create-button-inner (not (s/valid? ::person person-info)) person-info]))))

(defn update-button []
  (let [update-button-inner 
        (crud-button "Update"
                     #(swap! person-list assoc-in [:people @selected]
                            %))]
    (fn []
      (let [person-info @person]
        [update-button-inner (not (and (nat-int? @selected) (s/valid? ::person person-info))) person-info]))))

(defn delete-button []
  (let [delete-button-inner 
        (crud-button "Delete"
                     #(let [sel @selected] 
                        (swap! person-list update-in [:people]
                                                  (fn [v] (concat (subvec v 0 sel) (subvec v (inc sel)))))))]
    (fn []
      [delete-button-inner ((complement nat-int?) @selected)])))



(defn list-view []
  (let [{:keys [people prefix]} @person-list
        sel-person @selected]
    [:div.px-4.bg-white.border-solid.flex-none {:class "w-1/2"}
     (->> people
          (keep-indexed (fn [i {surname :surname :as p}] (when (.startsWith surname prefix) [i p])))
          (map (fn [ [i {:keys [name surname]}]]
                 (let [res (str name ", " surname)]
                   [:p {:key res 
                        :class (when (= sel-person i) "bg-blue-500") 
                        :on-click #(reset! selected i)} res]))))]))

(defn crud []
  [:div.font-bold.p-4.bg-gray-300
   [labelled-input "Filter prefix:" "filter" handle-prefix-change "w-1/2"]
   [:div.flex
    [list-view]
    [:div.ml-16 [labelled-input "Name:" "name" handle-input-change]
     [labelled-input "Surname:" "surname" handle-input-change]]]
   [:div.flex.justify-between.py-4 {:class "w-3/5"}
    [create-button]
    [update-button]
    [delete-button]]])

(defn ui []
  [:div.px-40
   [shd/challenge-section 5 "CRUD"]
   [crud]])

