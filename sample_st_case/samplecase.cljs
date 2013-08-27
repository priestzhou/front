(ns samplecase
    (:use-macros
        [dommy.macros :only (sel1)]
    )
    (:require
        [dommy.core :as dom]
        [dommy.template :as template]
    )
)

(defn ^:export wreck-it []
    (let [xhr (doto (js/XMLHttpRequest.) (.open "GET", "/ralph", false) (.send))
        response (.-responseText xhr)
        ]
        (when-not (= response "EOF")
            (dom/set-html! (sel1 :body) (+ (dom/html (sel1 :body)) response))
            (recur)
        )
    )
)
