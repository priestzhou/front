(ns utilities.core
)

(declare to-js-obj)

(defn- to-js-array [arr]
    (apply array (map to-js-obj arr))
)

(defn- to-js-map [dict]
    (apply js-obj (flatten 
        (for [[k v] dict] [(name k) (to-js-obj v)])
    ))
)

(defn to-js-obj [o]
    (cond
        (vector? o) (to-js-array o)
        (map? o) (to-js-map o)
        :else o
    )
)


(declare to-cljs-coll)

(defn to-cljs-vec [arr]
    (let [res (transient [])]
        (doseq [i (range (count arr))]
            (conj! res (to-cljs-coll (aget arr i)))
        )
        (persistent! res)
    )
)

(defn to-cljs-map [m]
    (let [res (transient {})
        ks (.keys js/Object m)
        ]
        (doseq [i (range (count ks))
            :let [k (aget ks i)]
            ]
            (assoc! res k (to-cljs-coll (aget m k)))
        )
        (persistent! res)
    )
)

(defn to-cljs-coll [o]
    (cond
        (instance? js/Array o) (to-cljs-vec o)
        ; this implementation is buggy for js array can be indexed by anything, 
        ; not only non-negative integers
        (instance? js/Object o) (to-cljs-map o)
        :else o
    )
)

