(ns samplecase
    (:use
        [jsmain :only (root-url load-page)]
    )
)

(defn sample []
    (.-innerHTML (.-body js/document))
)

(load-page (+ root-url "samplecase.html") sample)
