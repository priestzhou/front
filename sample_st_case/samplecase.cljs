(ns samplecase
    (:require
        [domina :as domina]
    )
    (:use
        [domina.xpath :only (xpath)]
    )
)

(defn ^:export wreck-it []
    (let [xhr (doto (js/XMLHttpRequest.) (.open "GET", "/ralph", false) (.send))
        response (.-responseText xhr)
        ]
        (when-not (= response "EOF")
            (domina/append! (xpath "//body") response)
            (recur)
        )
    )
)
