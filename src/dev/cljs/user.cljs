(ns cljs.user
  (:require [clojure.core.protocols :refer [Datafiable]]
            [portal.web :as p]))

(def portal (p/open))

(add-tap #'p/submit)
(tap> :hello)
(defn js->clj+
  "For cases when built-in js->clj doesn't work. Source: https://stackoverflow.com/a/32583549/4839573"
  [x]
  (into {} (for [k (js-keys x)] [(keyword k) (aget x k)])))

(extend-protocol Datafiable
  js/KeyboardEvent
  (datafy [this] (js->clj+ this)))
