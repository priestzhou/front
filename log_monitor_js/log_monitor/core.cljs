(ns log-monitor.core
    (:require
        [clojure.string :as str]
        [domina :as dom]
    )
    (:use
        [domina.xpath :only (xpath)]
        [utilities.core :only (to-js-obj to-cljs-coll)]
    )
)

(def default-config {
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
    }
)

(defn ^:export draw-column-chart []
    (let [
        config (merge default-config {
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

(defn extract-table-data [data]
    (when-not (empty? data)
        (let [ks (vec (for [[k] (get data 0)] k))]
            [
                ks
                (vec
                    (for [row data]
                        (vec
                            (for [k ks]
                                (get row k)
                            )
                        )
                    )
                )
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

(defn ^:export refresh [data]
    (let [data (to-cljs-coll data)]
        (draw-column-chart)
        (show-log-list (get data "logtable"))
        (show-group-table (get data "grouptable"))
    )
)
