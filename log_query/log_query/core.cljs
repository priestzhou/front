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

(def default-config-column {
    :chart {
        :type "column"
        :height 100
        :animation false
    }
    :credits {
        :enabled false
    }
    :title { 
        :text ""
    }
    :subtitle { 
        :text "" 
    }
    :legend false
    :xAxis { 
        :title {
            :text ""
        }
    }
    :yAxis {
        :min 0
        :title {
            :text ""
        }
    }
    :tooltip {
        :pointFormat "{point.y:,.0f}"
        :headerFormat ""
    }
    :plotOptions {
        :column {
            :animation false
            :pointWidth 15
            :pointPadding 0.2
        }
    }
    :series []
})

(def data (atom {"meta" [] "grouptable" [] "logtable" []}))

(defn parse-date [date-str]
    (let [[year month day hour minute sec] (map int (str/split date-str #"[\s-:]"))
        utc (.UTC js/Date year (dec month) day hour minute sec)
        ]
        utc
    )
)

(defn draw-search-count-chart []
    (let [d (get @data "matchchart")
        yaxis (get d "search-count")
        xaxis (get d "time-series")
        config (nested-merge default-config-column 
            {
                :series [
                    {
                        :data (vec (for [[datetime value] (zip xaxis yaxis)]
                            [(parse-date datetime) value]
                        ))
                    }
                ]
                :xAxis {
                    :type "datetime"
                }
            }
        )
        ]
        (.setOptions js/Highcharts (->js-obj {:global {:useUTC false}}))
        (.highcharts (js/jQuery "#VisualChartDiv") (->js-obj config))
        (dom/set-text! (sel1 :#matched_count) 
            (format "%d个匹配事件" (reduce + (get d "search-count")))
        )
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
    (-> (sel1 (sel1 (sel1 :#divContentList) :div) :table)
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
        ["timestamp"]
        (sort (into #{}
            (for [x gmeta
                k (keys (get x "gKeys"))
                ]
                k
            )
        ))
        (sort (into #{}
            (for [x gmeta
                k (keys x)
                :when (not= k "gKeys")
                ]
                k
            )
        ))
    ))
)

(defn extract-data [topic gmeta event]
    (cond
        (contains? event topic) (get event topic)
        :else (let [gid (get event "gId")
            gkeys (get (get gmeta gid) "gKeys")
            ]
            (if-let [x (get gkeys topic)]
                x
                ""
            )
        )
    )
)

(defn reformat-data [topics gmeta data]
    (vec (for [row data
        event (get row "events")
        ]
        (vec (cons 
            (get row "timestamp")
            (for [topic (rest topics)]
                (extract-data topic gmeta event)
            )
        ))
    ))
)

(defn reformat-group-table [gmeta data]
    (let [topics (extract-topics gmeta)]
        [
            topics
            (reformat-data topics gmeta data)
        ]
    )
)

(defn show-group-table []
    (let [dat @data
        gmeta (get dat "meta")
        da (get dat "grouptable")
        ]
        (when-not (empty? da)
            (let [[topics d] (reformat-group-table gmeta da)]
                (-> (sel1 (sel1 (sel1 :#divContentTable) :div) :table)
                    (dom/replace! (format-table topics d))
                )
            )
        )
    )
)

(defn show-ready []
    (-> (sel1 :.events)
        (dom/remove-class! :eventsNumLoading)
        (dom/add-class! :eventsNumOk)
    )
)

(defn show-busy []
    (-> (sel1 :.events)
        (dom/remove-class! :eventsNumOk)
        (dom/add-class! :eventsNumLoading)
    )
)

(defn refresh [d]
    (reset! data d)
    (draw-search-count-chart)
    (show-log-list)
    (show-group-table)
    (show-ready)
)

(defn fetch-log-update-succeed [response]
    (refresh response)
)

(defn on-error [{:keys [status status-text]}]
    (.log js/console (format "fetch updates error: %d %s " status status-text))
)

(defn update-tables [id]
    (ajax/GET (ajax/uri-with-params "/query/get" {
            "query-id" id
            "timestamp" (.now js/Date)
        }) {
        :handler fetch-log-update-succeed
        :error-handler on-error
    })
)

(defn get-time-range []
    (-> (sel1 :#time_range_picker)
        (dom/value)
    )
)

(defn get-keywords []
    (-> (sel1 :#SearchBar_0_0_0_id)
        (dom/value)
    )
)

(defn request-search []
    (show-busy)
    (let [time-range (get-time-range)
        keywords (get-keywords)
        interval (if (= time-range 60) 5000 10000)
        ]
        (ajax/POST (ajax/uri-with-params "/query/create" {
                :query keywords 
                :timewindow time-range
            }) {
            :handler (fn [response]
                (when-let [qid (get response "query-id")]
                    (update-tables qid)
                )
            )
            :error-handler on-error
        })
    )
)

(defn show-detail-section [type]
    (let [subsections {
            :list [(sel1 :#btn_switcher_list) (sel1 :#divContentList)]
            :table [(sel1 :#btn_switcher_table) (sel1 :#divContentTable)]
        }
        ]
        (doseq [[k [btn div]] subsections]
            (if (= k type)
                (do
                    (dom/add-class! btn "selected")
                    (dom/remove-class! div "hidden")
                )
                (do
                    (dom/remove-class! btn "selected")
                    (dom/add-class! div "hidden")
                )
            )
        )
    )
)

(defn click-on-time-picker [evt]
    (doto (sel1 :div.timeRangeMenu)
        (dom/remove-class! "hidden")
    )
    (.stopPropagation evt)
    (.preventDefault evt)
)

(defn cancel-time-picker []
    (doto (sel1 :#menu_lvl2)
        (dom/add-class! "hidden")
    )
    (doto (sel1 :div.timeRangeMenu)
        (dom/add-class! "hidden")
    )
)

(defn click-on-readtime-btn [evt]
    (doto (sel1 :#menu_lvl2)
        (dom/toggle-class! "hidden")
    )
    (.stopPropagation evt)
    (.preventDefault evt)
)

(defn highlight-choices [ev]
    (doto (.-target ev)
        (dom/add-class! "splMenuMouseOver")
    )
)

(defn unhighlight-choices [ev]
    (doto (.-target ev)
        (dom/remove-class! "splMenuMouseOver")
    )
)

(defn all-time-picker-li []
    (vec (for [div (sel :div.outerMenuWrapper)
        :let [ul (sel1 div :ul)]
        li (sel ul :li)
        ]
        li
    ))
)

(defn select-time-range [time-range]
    (doto (sel1 :#time_range_picker)
        (dom/set-value! time-range)
    )
    (case time-range
        60 (dom/set-text! (sel1 :#time_range_activator) "1分钟")
        300 (dom/set-text! (sel1 :#time_range_activator) "5分钟")
        (.log js/console (format "unknown time range: %d" time-range))
    )
)

(defn ^:export load []
    (show-detail-section :table)

    (dom/listen! (sel1 :#btn_switcher_list) 
        :click (partial show-detail-section :list)
    )
    (dom/listen! (sel1 :#btn_switcher_table) 
        :click (partial show-detail-section :table)
    )

    (dom/listen! (sel1 :#search_btn)
        :click request_search
    )

    (dom/listen! (sel1 :#TimeRangePicker_0_1_0)
        :click click-on-time-picker
    )
    (dom/listen! (sel1 :body)
        :click cancel-time-picker
    )
    (dom/listen! (sel1 :#realtime_btn)
        :click click-on-readtime-btn
    )
    (doseq [x (all-time-picker-li)]
        (dom/listen! x
            :mouseover highlight-choices
            :mouseout unhighlight-choices
        )
    )
    (dom/listen! (sel1 :#realtime_1min)
        :click (partial select-time-range 60)
    )
    (dom/listen! (sel1 :#realtime_5min)
        :click (partial select-time-range 300)
    )
)
