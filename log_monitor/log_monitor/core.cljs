(ns log-monitor.core
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
        [utilities.core :only (->js-obj ->cljs-coll)]
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
            :animation false
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

(defn extract-scc-xaxis [xaxis]
    (vec (map #(second (str/split % #" ")) xaxis))
)

(defn draw-search-count-chart [data]
    (let [config (merge default-config-column {
            :series [{:name "whatever" :data (get data "search-count")}]
            :xAxis {
                :categories (extract-scc-xaxis (get data "time-series"))
                :title {:text ""}
            }
        })
        ]
        (.highcharts (js/jQuery "#VisualChartDiv") (->js-obj config))
        (dom/set-text! (sel1 :#matched_count) 
            (format "%d个匹配事件" (reduce + (get data "search-count")))
        )
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

(defn draw-request-chart []
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
                    :formatter (fn []
                        (this-as me
                            (format "%s 总计%d次" 
                                (.-category (.-point me)) 
                                (.-y (.-point me))
                            )
                        )
                    )
                }
            }
        )
        ]
        (.highcharts (js/jQuery "#divContentChart") (->js-obj config))
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
            (-> (sel1 (sel1 (sel1 :#divContentTable) :div) :table)
                (dom/replace! (format-table topics data))
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

(defn refresh 
    ([data]
        (draw-search-count-chart (get data "matchchart"))
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
    (.log js/console (format "fetch updates error: %d %s " status status-text))
)

(defn periodically-update [id]
    (ajax/GET (ajax/uri-with-params "/query/get" {
            "query-id" id
            "timestamp" (.now js/Date)
        }) {
        :handler fetch-log-update-succeed
        :error-handler on-error
    })
)

(def arranged-update (atom nil))

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
        (ajax/POST (ajax/uri-with-params "/query/create" {
                :query keywords 
                :timewindow time-range
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

(defn show-detail-section [type]
    (let [subsections {
            :list [(sel1 :#btn_switcher_list) (sel1 :#divContentList)]
            :table [(sel1 :#btn_switcher_table) (sel1 :#divContentTable)]
            :chart [(sel1 :#btn_switcher_chart) (sel1 :#divContentChart)]
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
)

(defn ^:export load []
    (draw-request-chart)
    (refresh)
    (show-detail-section :list)

    (dom/listen! (sel1 :#btn_switcher_list) 
        :click (partial show-detail-section :list)
    )
    (dom/listen! (sel1 :#btn_switcher_table) 
        :click (partial show-detail-section :table)
    )
    (dom/listen! (sel1 :#btn_switcher_chart) 
        :click (partial show-detail-section :chart)
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
