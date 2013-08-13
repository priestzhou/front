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

(defn format-logtable-header [ks]
    (str/join ""
        (flatten
            [
                "<tr>"
                (for [k ks]
                    (format "<th align='left'>%s</th>" k)
                )
                "</tr>"
            ]
        )
    )
)

(defn format-cell [data page page-size]
    (let [start (* page page-size)
        end (min (+ start page-size) (count data))
        ]
        (when (< start end)
            (str/join "" (flatten
                (for [row (subvec data start end)]
                    [
                        "<tr>"
                        (for [cell row] (format "<td>%s</td>" cell))
                        "</tr>"
                    ]
                )
            ))
        )
    )
)

(defn show-list 
    ([data page page-size]
        (let [data (to-cljs-coll data)]
            (when-not (empty? data)
                (let [[ks data] (reformat data)]
                    (-> (dom/by-id "divContentList")
                        (xpath "div")
                        (xpath "table")
                        (xpath "thead")
                        (dom/set-html! (format-logtable-header ks))
                    )
                    (-> (dom/by-id "divContentList")
                        (xpath "div")
                        (xpath "table")
                        (xpath "tbody")
                        (dom/set-html! (format-cell data page page-size))
                    )
                )
            )
        )
    )

    ([data page]
        (show-list data page 10)
    )
)
