(ns fulcro-expo.root
  (:require [fulcro.client.primitives :as prim :refer [defsc]]
            [fulcro-expo.react-native :as rn]
            [fulcro-expo.application :refer [reconciler]]))


(defsc Root [this props]
  {:initial-state      (fn [_] {})
   :query              []}
  (rn/view {:style {:height "100%" :width "100%" :justify-content "center" :align-items "center"}}
    (rn/text {:style {}} "Hello Fulcro!")))
