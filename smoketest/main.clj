(ns smoketest.main
    (:require
        [clojure.string :as str]
        [smoketest.core :as st]
        [utilities.shutil :as sh]
    )
    (:use
        [testing.core :only (suite main load-cases)]
    )
    (:gen-class)
)

(defn- ralph-oath []
    (let [server (st/start-jetty (st/handle-static-files 
                {"/samplecase.html" "@/resources/samplecase.html"
                    "/samplecase.js" "@/resources/samplecase.js"
                }
            )
            (st/handle-transitions
                (st/handle-get "/ralph" "text/html" "<h1>Ralph's Oath</h1>")
                (st/handle-get "/ralph" "text/html" "<p>I'm bad,</p>")
                (st/handle-get "/ralph" "text/html" "<p>and that's good.</p>")
                (st/handle-get "/ralph" "text/html" "<p>I will never be good,</p>")
                (st/handle-get "/ralph" "text/html" "<p>but that's not bad.</p>")
                (st/handle-get "/ralph" "text/plain" "EOF")
            )
        )
        page (sh/execute ["phantomjs" "build/front_st_samplecase.js"])
        res (st/wait-for-result server)
        ]
        (str/replace res #"[\n]" "")
    )
)

(suite "ralph oath"
    (:fact samplecase
        ralph-oath
        :eq
        (fn []
            (str/join [
                "<h1>Ralph's Oath</h1>"
                "<p>I'm bad,</p>"
                "<p>and that's good.</p>"
                "<p>I will never be good,</p>"
                "<p>but that's not bad.</p>"
            ])
        )
    )
)

(defn -main [& args]
    (->> (load-cases 'smoketest.main)
        (main args)
    )
)
