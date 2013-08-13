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
