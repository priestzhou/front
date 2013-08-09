(ns smoketest.core
    (:require
        [clojure.data.json :as json]
        [clojure.java.io :as io]
        [utilities.shutil :as sh]
    )
)

(def ^:private result (atom nil))

(defn post-result [req]
    (when (and
            (= (:uri req) "/st-result")
            (= (:request-method req) :post)
            (.startsWith (:content-type req) "text/plain")
        )
        (->> req
            (:body)
            (io/reader)
            (json/read)
            (reset! result)
        )
        {:status 202 :headers {"Access-Control-Allow-Origin" "*"}}
    )
)

(def ^:private transition-handlers (atom nil))

(defn- transitable-handler [req]
    (let [hs @transition-handlers]
        (when-not (empty? hs)
            (let [[h] hs
                    res (h req)
                ]
                (when res
                    (swap! transition-handlers rest)
                    res
                )
            )
        )
    )
)

(defn handle-transitions [& handlers]
    (reset! transition-handlers handlers)
    transitable-handler
)

(defn- static-files-handler [files req]
    (when (= (:request-method req) :get)
        (let [p (:uri req)
                [f type] (files p)
            ]
            (when f
                {:status 200
                    :headers {"content-type" type}
                    :body (sh/open-file f)
                }
            )
        )
    )
)

(defn- mime-type [f]
    (cond
        (.endsWith f ".html") "text/html"
        (.endsWith f ".css") "text/css"
        (.endsWith f ".js") "application/javascript"
        :else (throw 
            (IllegalArgumentException. (str "unknown file type: " f))
        )
    )
)

(defn handle-static-files [files]
    (let [wrapped (apply merge 
                (for [[p f] files]
                    {p [f (mime-type f)]}
                )
            )
        ]
        (partial static-files-handler wrapped)
    )
)

(defn wait-for-result [server]
    (if-not @result
        (do
            (Thread/sleep 1000)
            (recur server)
        )
        (do
            (Thread/sleep 100)
            (.stop server)
            @result
        )
    )
)

