(ns fulcro-expo.application)

(defonce app (atom nil))

(defn reconciler []
  (-> app deref :reconciler))

;;
;; debug/testing utils
;;
(defn- debug-state []
  @(fulcro.client.primitives/app-state (reconciler)))
