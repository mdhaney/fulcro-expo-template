(ns fulcro-expo.app
  (:require
    [shadow.expo :as expo]
    [fulcro.client :as fc]
    [fulcro-expo.application :as app]
    [fulcro-expo.root :as root]))

(defn start
  {:dev/after-load true}
  []
  (reset! app/app (fc/mount @app/app root/Root :i-got-no-dom-node)))

(defn init []
  (let [my-app (fc/make-fulcro-client
                 {:reconciler-options {:root-render  expo/render-root
                                       :root-unmount (fn [node])}})]

    (reset! app/app my-app)
    (start)))


