(ns fulcro-expo.application
  (:require
    [com.fulcrologic.fulcro.application :as app]
    [com.fulcrologic.fulcro.rendering.keyframe-render :as kr]
    ["expo" :as expo]
    ["create-react-class" :as crc]
    [taoensso.timbre :as log]))

(defonce root-ref (atom nil))
(defonce root-component-ref (atom nil))

(defn render-root [root]
  (try
    (let [first-call? (nil? @root-ref)]
      (reset! root-ref root)

      (if-not first-call?
        (when-let [root @root-component-ref]
          (.forceUpdate ^js root)
          root-component-ref)
        (let [Root
              (crc
                #js {:componentDidMount
                     (fn []
                       (this-as this
                         (reset! root-component-ref this)))
                     :componentWillUnmount
                     (fn []
                       (reset! root-component-ref nil))
                     :render
                     (fn []
                       (let [body @root-ref]
                         (log/spy :info body)
                         (try
                           (if (fn? body)
                             (body)
                             body)
                           (catch :default e
                             (log/error e "Render failed")))))})]
          (expo/registerRootComponent Root)
          root-component-ref)))
    (catch :default e
      (log/error e "Unable to mount/refresh"))))


(defonce app
  (app/fulcro-app
    {:optimized-render! kr/render!
     :render-root!      render-root}))

;; debug/testing utils
;;
(defn- debug-state []
  (app/current-state app))
