(ns log-query.core
    (:use-macros
        [dommy.macros :only (sel sel1 node deftemplate)]
    )
    (:require
        [clojure.string :as str]
        [dommy.core :as dom]
        [dommy.template :as template]
        [ajax.core :as ajax]
    )
    (:use
        [utilities.core :only (->js-obj ->cljs-coll enumerate zip nested-merge)]
    )
)

(def data (atom {}))

(defn show-search-count []
    (if-let [d (get @data "total")]
        (dom/set-text! (sel1 :#matched_count) 
            (format "%d个匹配日志" d)
        )
        (.log js/console "total number of logs is missing.")
    )
)

(defn extract-table-data [data]
    [
        (get data "header")
        (get data "data")
    ]
)

(defn format-header [topics]
    [:thead
        [:tr
            (for [k topics]
                [:th {:align "left"} k]
            )
        ]
    ]
)

(defn format-cell [data page page-size]
    (let [start (* page page-size)
        end (min (+ start page-size) (count data))
        ]
        (when (< start end)
            [:tbody
                (for [row (subvec data start end)]
                    [:tr
                        (for [cell row] [:td cell])
                    ]
                )
            ]
        )
    )
)

(defn format-table 
    ([topics data page page-size]
        (template/compound-element
            [:table {:class "table table-striped" :style "border-bottom:1px solid #DDDDDD"}
                (format-header topics)
                (format-cell data page page-size)
            ]
        )
    )
    ([topics data]
        (format-table topics data 0 (count data))
    )
)

(defn update-log-list [ks data page page-size]
    (-> (sel1 (sel1 :#div_content_list) :table)
        (dom/replace! (format-table ks data page page-size))
    )
)

(defn show-pager [ks data page page-size]
    (-> (js/jQuery "#pagination")
        (.pagination (count data)
            (->js-obj {
                :callback (fn [page]
                    (update-log-list ks data page page-size)
                )
                :prev_text "上一页"
                :next_text "下一页"
                :link_to "javascript:void(0)"
                :items_per_page page-size
                :num_edge_entries 1
                :num_display_entries 3
                :current_page page
                :noPreNextCurrentCss true
            })
        )
    )
)

(defn show-log-list 
    ([data page page-size]
        (when-not (empty? data)
            (let [[ks data] (extract-table-data data)]
                (update-log-list ks data page page-size)
                (show-pager ks data page page-size)
            )
        )
    )

    ([]
        (show-log-list (get @data "logtable") 0 10)
    )
)

(defn extract-topics [gmeta]
    (vec (concat 
        (into (sorted-set)
            (for [x gmeta
                k (keys (get x "gKeys"))
                ]
                k
            )
        )
        (into (sorted-set)
            (for [x gmeta
                k (keys (get x "gValues"))
                ]
                k
            )
        )
    ))
)

(defn reformat-data [topics gmeta]
    (vec (for [m gmeta]
        (vec (for [t topics]
            (if-let [v (get (get m "gValues") t)]
                v
                (if-let [v (get (get m "gKeys") t)]
                    v
                    nil
                )
            )
        ))
    ))
)

(defn reformat-group-table [gmeta]
    (let [topics (extract-topics gmeta)]
        [
            topics
            (reformat-data topics gmeta)
        ]
    )
)

(defn show-group-table []
    (let [dat @data
        gmeta (get dat "results")
        ]
        (let [[topics d] (reformat-group-table gmeta)]
            (-> (sel1 :#div_content_table)
                (sel1 :table)
                (dom/replace! (format-table topics d))
            )
        )
    )
)

(defn show-ready []
    (-> (sel1 :#matched_count)
        (dom/remove-class! :EventsNumLoading)
        (dom/add-class! :EventsNumOk)
    )
)

(defn show-busy []
    (-> (sel1 :#matched_count)
        (dom/remove-class! :EventsNumOk)
        (dom/add-class! :EventsNumLoading)
    )
)

(defn fetch-result-succeed [response]
    (swap! data merge response)
    (show-group-table)
    (when (= (count @data) 3)
        (show-ready)
    )
)

(defn fetch-log-succeed [response]
    (swap! data merge response)
    (show-log-list)
    (show-search-count)
    (when (= (count @data) 3)
        (show-ready)
    )
)

(defn on-error [{:keys [status status-text]}]
    (.log js/console (format "fetch updates error: %d %s " status status-text))
)

(defn fetch-results [id]
    (ajax/GET (ajax/uri-with-params "/query/result" {
            "query-id" id
        }) {
        :handler fetch-result-succeed
        :error-handler on-error
    })
    (ajax/GET (ajax/uri-with-params "/query/log" {
            "query-id" id
        }) {
        :handler fetch-log-succeed
        :error-handler on-error
    })
)

(defn get-time [ctrl]
    (try
        (-> ctrl
            (sel1)
            (dom/value)
            (js/Date.)
            (.getTime)
        )
    (catch js/RangeError ex
        (-> ctrl
            (name)
            (js/RangeError.)
            (throw)
        )
    ))
)

(defn get-keywords []
    (-> (sel1 :#search_bar)
        (dom/value)
    )
)

(defn request-search []
    (let [
        start-time (get-time :#start_time)
        end-time (get-time :#end_time)
        keywords (get-keywords)
        ]
        (if (>= start-time end-time)
            (.log js/console "start must be less than end")
            (do
                (show-busy)
                (ajax/POST (ajax/uri-with-params "/query/create" {
                        :query keywords 
                        :start start-time
                        :end end-time
                    }) {
                    :handler (fn [response]
                        (when-let [qid (get response "query-id")]
                            (fetch-results qid)
                        )
                    )
                    :error-handler on-error
                })
            )
        )
    )
)

(defn show-detail-section [type]
    (let [subsections {
            :list [(sel1 :#btn_switcher_list) (sel1 :#div_content_list)]
            :table [(sel1 :#btn_switcher_table) (sel1 :#div_content_table)]
        }
        ]
        (doseq [[k [btn div]] subsections]
            (if (= k type)
                (do
                    (dom/add-class! btn "Selected")
                    (dom/remove-attr! div :hidden)
                )
                (do
                    (dom/remove-class! btn "Selected")
                    (dom/set-attr! div :hidden)
                )
            )
        )
    )
)

(defn format-datetime [year month day hour minute sec]
    (format "%04d-%02d-%02dT%02d:%02d:%02d" year month day hour minute sec)
)

(defn set-time [ctrl datetime]
    (let [year (.getFullYear datetime)
        month (inc (.getMonth datetime))
        day (.getDate datetime)
        hour (.getHours datetime)
        minute (.getMinutes datetime)
        sec 0
        ]
        (dom/set-value! (sel1 ctrl) 
            (format-datetime year month day hour minute sec)
        )
    )
)

(defn ->int' [s i]
    (if (empty? s)
        i
        (let [[x & xs] s]
            (case x
                \0 (recur xs (* i 10))
                \1 (recur xs (+ (* i 10) 1))
                \2 (recur xs (+ (* i 10) 2))
                \3 (recur xs (+ (* i 10) 3))
                \4 (recur xs (+ (* i 10) 4))
                \5 (recur xs (+ (* i 10) 5))
                \6 (recur xs (+ (* i 10) 6))
                \7 (recur xs (+ (* i 10) 7))
                \8 (recur xs (+ (* i 10) 8))
                \9 (recur xs (+ (* i 10) 9))
            )
        )
    )
)

(defn ->int [s]
    (->int' s 0)
)

(def datetime-pattern #"(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2})")

(defn parse-datetime [datetime-str]
    (if-let [m (re-find datetime-pattern datetime-str)]
        (map ->int (rest m))
    )
)

(defn on-time-change [inp cal hhmm]
    (if-let [v (dom/value (sel1 inp))]
        (let [[year month day _ _ sec] (parse-datetime v)
            hour (quot hhmm 100)
            minute (rem hhmm 100)
            ]
            (dom/set-value! (sel1 inp)
                (format-datetime year month day hour minute sec)
            )
        )
    )
)

(defn on-start-time-change [cal hhmm]
    (on-time-change :#start_time cal hhmm)
)

(defn on-end-time-change [cal hhmm]
    (on-time-change :#end_time cal hhmm)
)

(defn on-date-change [inp cal date]
    (if-let [v (dom/value (sel1 inp))]
        (let [[_ _ _ hour minute sec] (parse-datetime v)
            year (.getFullYear date)
            month (+ (.getMonth date) 1)
            day (.getDate date)
            ]
            (dom/set-value! (sel1 inp)
                (format-datetime year month day hour minute sec)
            )
        )
    )
)

(defn on-start-date-change [cal date]
    (on-date-change :#start_time cal date)
)

(defn on-end-date-change [cal date]
    (on-date-change :#end_time cal date)
)

(defn ^:export load []
    (let [now (Date/now)
        end-time (js/Date. now)
        start-time (js/Date. (- now 3600000))
        ]
        (set-time :#start_time start-time)
        (set-time :#end_time end-time)
    )
    (show-detail-section :list)

    (dom/listen! (sel1 :#btn_switcher_list) 
        :click (partial show-detail-section :list)
    )
    (dom/listen! (sel1 :#btn_switcher_table) 
        :click (partial show-detail-section :table)
    )

    (dom/listen! (sel1 :#search_btn)
        :click request-search
    )
)
