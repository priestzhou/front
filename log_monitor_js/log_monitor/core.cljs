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

(defn reformat [data]
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

(defn format-logtable-header [topics]
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

(defn format-log-table [topics data page page-size]
    (str/join "" (flatten
        (concat
            (format-logtable-header topics)
            (format-cell data page page-size)
        )
    ))
)

(defn ^:export show-log-list 
    ([data page page-size]
        (let [data (to-cljs-coll data)]
            (when-not (empty? data)
                (let [[ks data] (reformat data)]
                    (-> (dom/by-id "divContentList")
                        (xpath "div")
                        (xpath "table")
                        (dom/set-html! (format-log-table ks data page page-size))
                    )
                )
            )
        )
    )

    ([data page]
        (show-log-list data page 10)
    )
)

