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
            (nil? @result)
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

