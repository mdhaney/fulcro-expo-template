(ns fulcro-expo.app
  (:require
    [com.fulcrologic.fulcro.application :as app]
    [fulcro-expo.application :as expo-app]
    [fulcro-expo.root :as root]))

(defn start
  {:dev/after-load true}
  []
  (app/mount! expo-app/app root/Root :i-got-no-dom-node))

(defn init []
  (start))


