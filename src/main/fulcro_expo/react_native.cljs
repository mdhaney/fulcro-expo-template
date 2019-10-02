(ns fulcro-expo.react-native
  (:require ["react-native" :as rn]
            ["react" :as r]
            [clojure.string :as str]
            [clojure.walk :as w]
            [clojure.set :as set]))

(def create-element r/createElement)

;; copy from natal-shell
(def camel-rx #"([a-z])([A-Z])")

(defn to-kebab [s]
  (-> s
      (str/replace camel-rx "$1-$2")
      (str/replace "." "-")
      str/lower-case))

(defn sp [js-name]
  (str/split js-name #"\."))

(defn kebab-case->camel-case
  "Converts from kebab case to camel case, eg: on-click to onClick"
  [input]
  (let [words (str/split input #"-")
        capitalize (->> (rest words)
                        (map #(apply str (str/upper-case (first %)) (rest %))))]
    (apply str (first words) capitalize)))

(defn map-keys->camel-case
  "Stringifys all the keys of a cljs hashmap and converts them
   from kebab case to camel case. If :html-props option is specified,
   then rename the html properties values to their dom equivalent
   before conversion"
  [data & {:keys [html-props]}]
  (let [convert-to-camel (fn [[key value]]
                           [(kebab-case->camel-case (name key)) value])]
    (w/postwalk (fn [x]
                  (if (map? x)
                    (let [new-map (if html-props
                                    (set/rename-keys x {:class :className :for :htmlFor})
                                    x)]
                      (into {} (map convert-to-camel new-map)))
                    x))
                data)))

(defn element [element opts & children]
  (if element
    (apply create-element element
           (clj->js (map-keys->camel-case opts :html-props true))
           children)))

(defn partial-element [& args]
  (-> (apply partial element args)
      (with-meta {:rn-element? true})))

;; apis
(def platform rn/Platform)
(def style-sheet rn/StyleSheet)

(defn create-style-sheet [styles]
  (rn/StyleSheet.create
    (clj->js
      (zipmap (keys styles)
              (map #(map-keys->camel-case % :html-props true) (vals styles))))))

;; elements
(def activity-indicator (partial-element rn/ActivityIndicator))
(def button (partial-element rn/Button))
(def image (partial-element rn/Image))
(def image-background (partial-element rn/ImageBackground))
(def modal (partial-element rn/Modal))
(def text (partial-element rn/Text))
(def text-input (partial-element rn/TextInput))
(def touchable-highlight (partial-element rn/TouchableHighlight))
(def touchable-opacity (partial-element rn/TouchableOpacity))
(def view (partial-element rn/View))

;; iOS specific elements
(def date-picker-ios (partial-element rn/DatePickerIOS))

;; android specific elements

;; utils
(defn ios? []
  (= "ios" (.-OS platform)))

(defn android? []
  (= "android" (.-OS platform)))

(defn ss-override [theme style new-props]
  (let [props (js->clj (.flatten style-sheet (theme style)) :keywordize-keys true)]
    (assoc theme style (merge props new-props))))