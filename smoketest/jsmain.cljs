(ns jsmain)

(def root-url "http://localhost:11111/")

(defn load-page [url f]
    (try
        (let [page (.create (js/require "webpage"))]
            (.open page url
                (fn [status]
                    (when-not (= status "success")
                        (throw (js/Error. (str "fail to load page: " status)))
                    )
                    (let [x (.evaluate page f)
                            xhr (js/XMLHttpRequest.)
                        ]
                        (.open xhr "POST" (+ root-url "st-result") false)
                        (.setRequestHeader xhr "Content-Type" "text/plain")
                        (.send xhr (.stringify js/JSON x))
                        (.exit js/phantom 0)
                    )
                )
            )
        )
    (catch js/Error ex
        (.log js/console ex)
        (.exit js/phantom 1)
    ))
)

