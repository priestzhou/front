(ns log-monitor.core
    (:require
        [clojure.string :as str]
        [domina :as dom]
        [ajax.core :as ajax]
    )
    (:use
        [domina.xpath :only (xpath)]
        [utilities.core :only (to-js-obj to-cljs-coll)]
    )
)

(def default-config-column {
    :chart {
        :type "column"
        :height 100
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
        :categories []
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
            :pointWidth 15
            :pointPadding 0.2
        }
    }
    :series []
})

(def default-config-area {
    :chart {
        :type "area"
        :zoomType "x"
    }
    :credits {
        :enabled false
    }
    :title {:text ""}
    :subtitle {:text ""}
    :legend {
        :layout "vertical"
        :align "left"
        :verticalAlign "top"
        :x 100
        :y 0
        :floating true
        :borderWidth 1
        :backgroundColor "#FFFFFF"
    }
    :xAxis {
        :title {:text ""}
    }
    :yAxis { 
        :min 1
        :title {
            :text ""
        }
    }
    :plotOptions {
        :area {
            :fillColor {
                :linearGradient {:x1 0 :y1 0 :x2 0 :y2 1}
                :stops []
            }
            :lineWidth 1
            :marker {
                :enabled false
            }
            :shadow false
            :states {
                :hover {
                    :lineWidth 1
                }
            }
        }
    }
    :tooltip {
        :pointFormat "{point.y:,.0f}"
    }
    :series []
})

(defn ^:export draw-column-chart []
    (let [config (merge default-config-column {
            :series [{:name "test" :data [1 2 3 4 5 6]}]
            :xAxis {
                :categories ["A" "B" "C" "D" "E" "F"]
                :title {:text ""}
            }
        })
        ]
        (.highcharts (js/jQuery "#VisualChartDiv") (to-js-obj config))
    )
)

(defn calc-series [data]
    {:series
        (vec (for [[ser dict] data]
            {:name ser :data (vec (for [[_ v] dict] v))}
        ))
    }
)

(defn calc-xaxis [data]
    (vec (sort
        (let [[_ snd] (first data)]
            (for [[k] snd] k)
        )
    ))
)

(defn ^:export draw-request-chart []
    (let [data {
            "data1" {"test1" 10 "test3" 15 "test2" 12 "test5" 12 "test4" 5 "test7" 2 "test6" 12 "test8" 9} 
            "data3" {"test1" 2 "test3" 6 "test2" 15 "test5" 23 "test4" 9 "test7" 17 "test6" 5 "test8" 7} 
            "data2" {"test1" 5 "test3" 10 "test2" 11 "test5" 13 "test4" 11 "test7" 21 "test6" 10 "test8" 19}
        }
        config (merge default-config-area
            (calc-series data)
            {
                :xAxis {:categories (calc-xaxis data)}
                :tooltip {
                    :headerFormat ""
                    :formatter js/request_chart_tooltip
                }
            }
        )
        ]
        (.highcharts (js/jQuery "#divContentChart") (to-js-obj config))
    )
)

(defn extract-table-data [data]
    (when-not (empty? data)
        (let [ks (vec (for [[k] (get data 0)] k))]
            [
                ks
                (vec (for [row data]
                    (vec (for [k ks]
                        (get row k)
                    ))
                ))
            ]
        )
    )
)

(defn format-header [topics]
    [
        "<thead>"
        "<tr>"
        (for [k topics]
            (format "<th align='left'>%s</th>" k)
        )
        "</tr>"
        "</thead>"
    ]
)

(defn format-cell [data page page-size]
    (let [start (* page page-size)
        end (min (+ start page-size) (count data))
        ]
        (when (< start end)
            [
                "<tbody>"
                (for [row (subvec data start end)]
                    [
                        "<tr>"
                        (for [cell row] (format "<td>%s</td>" cell))
                        "</tr>"
                    ]
                )
                "</tbody>"
            ]
        )
    )
)

(defn format-table 
    ([topics data page page-size]
        (str/join "" (flatten
            (concat
                (format-header topics)
                (format-cell data page page-size)
            )
        ))
    )
    ([topics data]
        (format-table topics data 0 (count data))
    )
)

(defn update-log-list [ks data page page-size]
    (-> (dom/by-id "divContentList")
        (xpath "div")
        (xpath "table")
        (dom/set-html! (format-table ks data page page-size))
    )
)

(defn ^:export show-pager [ks data page page-size]
    (-> (js/jQuery "#pagination")
        (.pagination (count data)
            (to-js-obj {
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

    ([data]
        (show-log-list data 0 10)
    )
)

(defn flatten-gkeys [data]
    (vec (for [row data]
        (merge 
            (dissoc row "gKeys")
            (get row "gKeys")
        )
    ))
)

(defn reformat-group-table [data]
    (extract-table-data
        (flatten-gkeys data)
    )
)

(defn show-group-table [data]
    (when-not (empty? data)
        (let [[topics data] (reformat-group-table data)]
            (-> (dom/by-id "divContentTable")
                (xpath "div")
                (xpath "table")
                (dom/set-html! (format-table topics data))
            )
        )
    )
)

(defn show-ready []
    (-> (dom/by-class "events")
        (dom/remove-class! "eventsNumLoading")
        (dom/add-class! "eventsNumOk")
    )
)

(defn show-busy []
    (-> (dom/by-class "events")
        (dom/remove-class! "eventsNumOk")
        (dom/add-class! "eventsNumLoading")
    )
)

(defn ^:export refresh 
    ([data]
        (draw-column-chart)
        (show-log-list (get data "logtable"))
        (show-group-table (get data "grouptable"))
        (show-ready)
    )

    ([] (refresh {"grouptable" [] "logtable" []}))
)

(defn fetch-log-update-succeed [response]
    (refresh response)
)

(defn on-error [{:keys [status status-text]}]
    (dom/log (format "fetch updates error: %d %s " status status-text))
)

(defn periodically-update [id]
    (ajax/GET (str "/query/get?query-id=" id) {
        :handler fetch-log-update-succeed
        :error-handler on-error
    })
)

(def arranged-update (atom nil))

(defn get-time-range []
    (-> (dom/by-id "time_range_picker")
        (dom/value)
    )
)

(defn get-keywords []
    (-> (dom/by-id "SearchBar_0_0_0_id")
        (dom/value)
    )
)

(defn ^:export request-search []
    (swap! arranged-update (fn [itv]
        (when itv
            (js/clearInterval itv)
            nil
        )
    ))
    (show-busy)
    (let [time-range (get-time-range)
        keywords (get-keywords)
        interval (if (= time-range 60) 5000 10000)
        ]
        (ajax/POST 
            (ajax/uri-with-params "/query/create" {
                "querystring" keywords 
                "timewindow" time-range
            }) {
            :handler (fn [response]
                (when-let [qid (get response "query-id")]
                    (periodically-update qid)
                    (reset! arranged-update
                        (js/setInterval
                            (partial periodically-update qid)
                            interval
                        )
                    )
                )
            )
            :error-handler on-error
        })
    )
)
