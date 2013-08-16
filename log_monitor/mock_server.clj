(ns log-monitor.mock-server
    (:require
        [utilities.web :as web]
    )
    (:gen-class)
)

(defn post-for-queryid [req]
    (when (and
            (= (:request-method req) :post)
            (= (:uri req) "/query/create")
        )
        (println "post-for-queryid")
        (println req)
        (println (slurp (:body req)))
        {:status 202
            :headers {
                "Access-Control-Allow-Origin" "*"
                "content-type" "application/json"
            }
            :body "{\"query-id\": 1}"
        }
    )
)

(defn get-for-updating [req]
    (when (and
            (= (:request-method req) :get)
            (= (:uri req) "/query/get")
        )
        (println "get-for-updating")
        (prn req)
        {:status 202
            :headers {
                "Access-Control-Allow-Origin" "*"
                "content-type" "application/json"
            }
            :body "
{
    \"matchchart\": {
        \"time-series\": [\"2013-08-09 00:00:00\", \"2013-08-09 00:01:00\", \"2013-08-09 00:02:00\", \"2013-08-09 00:03:00\", \"2013-08-09 00:04:00\", \"2013-08-09 00:05:00\", \"2013-08-09 00:06:00\", \"2013-08-09 00:07:00\", \"2013-08-09 00:08:00\", \"2013-08-09 00:09:00\", \"2013-08-09 00:10:00\", \"2013-08-09 00:11:00\", \"2013-08-09 00:12:00\", \"2013-08-09 00:13:00\", \"2013-08-09 00:14:00\", \"2013-08-09 00:15:00\", \"2013-08-09 00:16:00\", \"2013-08-09 00:17:00\", \"2013-08-09 00:18:00\", \"2013-08-09 00:19:00\", \"2013-08-09 00:20:00\", \"2013-08-09 00:21:00\", \"2013-08-09 00:22:00\", \"2013-08-09 00:23:00\", \"2013-08-09 00:24:00\", \"2013-08-09 00:25:00\", \"2013-08-09 00:26:00\", \"2013-08-09 00:27:00\", \"2013-08-09 00:28:00\", \"2013-08-09 00:29:00\"],
        \"search-count\": [75, 81, 91, 71, 61, 9, 89, 69, 93, 36, 74, 47, 12, 12, 41, 90, 49, 55, 60, 10, 9, 25, 16, 11, 28, 40, 71, 44, 9, 80]
    },
    \"meta\": [
        {
            \"count_id_1\": 200, 
            \"gKeys\": {
                \"level\": \"INFO\",
                \"ip\": \"192.168.1.1\"
            }
        },
        {
            \"count_id_1\": 200, 
            \"gKeys\": {
                \"level\": \"DEBUG\",
                \"ip\": \"192.168.1.100\"
            }
        }
    ], 
    \"grouptable\": [
        {
            \"timestamp\": \"2013-08-09 00:00:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 32
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 10
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:01:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 77
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 2
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:02:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 96
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:03:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 100
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 9
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:04:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 38
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 6
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:05:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 40
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 6
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:06:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 85
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 4
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:07:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 80
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 2
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:08:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 95
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 8
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:09:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 24
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 8
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:10:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 24
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 10
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:11:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 29
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:12:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 5
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 1
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:13:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 11
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 4
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:14:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 41
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 1
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:15:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 70
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 2
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:16:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 25
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 8
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:17:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 23
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 7
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:18:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 60
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 5
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:19:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 8
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 4
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:20:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 18
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 10
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:21:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 3
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 7
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:22:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 83
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:23:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 58
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 4
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:24:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 95
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 9
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:25:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 6
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 2
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:26:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 11
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 4
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:27:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 30
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 7
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:28:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 41
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 1
                }    
            ]
        },
        {
            \"timestamp\": \"2013-08-09 00:29:00\",
            \"events\": [
                {
                    \"gId\": 0,
                    \"count_id_1\": 93
                },
                {
                    \"gId\": 1,
                    \"count_id_1\": 3
                }    
            ]
        }
    ], 
    \"logtable\": {
        \"header\": [\"timestamp\", \"level\", \"location\", \"id_1\", \"message\"],
        \"data\": [
            [\"2013-08-09 18:57:40\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"9196250142345063344\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_9196250142345063344_5277466\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-2293429873557112819\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2293429873557112819_3614594\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-1970220620268133764\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-1970220620268133764_2464569\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"2209152077006569192\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_2209152077006569192_2165825\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-9032564916765756352\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-9032564916765756352_544146\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"1584592962350205741\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_1584592962350205741_3424548\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-3500705922293771948\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-3500705922293771948_537720\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"7748586760585967980\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7748586760585967980_5246522\"],
            [\"2013-08-09 18:57:43\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"2257423460492806845\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_2257423460492806845_11147352\"],
            [\"2013-08-09 18:57:45\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-3974105409641541931\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-3974105409641541931_11472021\"],
            [\"2013-08-09 18:57:45\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"993877546571983183\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_993877546571983183_2482450\"],
            [\"2013-08-09 18:57:47\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"6167147539767587327\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6167147539767587327_11488401\"],
            [\"2013-08-09 18:57:48\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-7686243307053460358\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7686243307053460358_3458057\"],
            [\"2013-08-09 18:57:48\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-7608766485581267526\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7608766485581267526_673460\"],
            [\"2013-08-09 18:57:50\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-4766643123015059895\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4766643123015059895_1078942\"],
            [\"2013-08-09 18:57:50\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-5663234269452667849\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5663234269452667849_2480975\"],
            [\"2013-08-09 18:57:50\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"381196251613344840\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_381196251613344840_612342\"],
            [\"2013-08-09 18:57:51\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"577071493286575335\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_577071493286575335_11492776\"],
            [\"2013-08-09 18:57:51\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-4366800936733136497\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4366800936733136497_5283316\"],
            [\"2013-08-09 18:57:52\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"6276506500660167231\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6276506500660167231_398775\"],
            [\"2013-08-09 18:57:52\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-5500941694965839445\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5500941694965839445_2480214\"],
            [\"2013-08-09 18:57:54\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"6656406666893049435\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6656406666893049435_2128292\"],
            [\"2013-08-09 18:57:54\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-5431553043909960947\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5431553043909960947_11205595\"],
            [\"2013-08-09 18:57:54\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-7418752015773360736\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7418752015773360736_4712912\"],
            [\"2013-08-09 18:57:54\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"7455678178861538954\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7455678178861538954_429938\"],
            [\"2013-08-09 18:57:54\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-2123995176868425694\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2123995176868425694_3515277\"],
            [\"2013-08-09 18:57:55\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"2330747764478432061\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_2330747764478432061_11119276\"],
            [\"2013-08-09 18:57:55\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"6904988708838685895\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6904988708838685895_5376673\"],
            [\"2013-08-09 18:57:55\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-4435719614067843117\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4435719614067843117_10388076\"],
            [\"2013-08-09 18:57:55\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"4278657071388830776\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_4278657071388830776_2929570\"],
            [\"2013-08-09 18:57:55\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-9205761362901501641\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-9205761362901501641_10615287\"],
            [\"2013-08-09 18:57:56\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"8291783686731032923\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8291783686731032923_11474279\"],
            [\"2013-08-09 18:57:56\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"4843875551018425615\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_4843875551018425615_11850532\"],
            [\"2013-08-09 18:57:57\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"5002500616427251683\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_5002500616427251683_4767964\"],
            [\"2013-08-09 18:57:58\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"8465959605068896725\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8465959605068896725_607996\"],
            [\"2013-08-09 18:57:58\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-2943287808487214523\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2943287808487214523_2583770\"],
            [\"2013-08-09 18:57:58\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"96217341267629501\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_96217341267629501_5003029\"],
            [\"2013-08-09 18:57:58\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-7115266324321469383\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7115266324321469383_2343469\"],
            [\"2013-08-09 18:57:58\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-695458870783336477\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-695458870783336477_10648289\"],
            [\"2013-08-09 18:57:58\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"9034437279673059450\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_9034437279673059450_2312943\"],
            [\"2013-08-09 18:57:58\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"1597830234837264456\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_1597830234837264456_2167989\"],
            [\"2013-08-09 18:57:59\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"7116995168261300326\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7116995168261300326_312688\"],
            [\"2013-08-09 18:58:00\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"5325532776085505375\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_5325532776085505375_133305\"],
            [\"2013-08-09 18:58:06\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"9166888087053030288\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_9166888087053030288_448220\"],
            [\"2013-08-09 18:58:06\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-9115951738129844453\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-9115951738129844453_2762506\"],
            [\"2013-08-09 18:58:07\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-8159745059722194250\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-8159745059722194250_486478\"],
            [\"2013-08-09 18:58:07\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"7395431284604724127\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7395431284604724127_11850648\"],
            [\"2013-08-09 18:58:10\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"5646409331255298552\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_5646409331255298552_398689\"],
            [\"2013-08-09 18:58:10\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"3326201913404078457\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_3326201913404078457_5196651\"],
            [\"2013-08-09 18:58:10\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"6711612843401554204\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6711612843401554204_2198766\"],
            [\"2013-08-09 18:58:13\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"8491413143793969289\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8491413143793969289_3614360\"],
            [\"2013-08-09 18:58:14\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-4500985149115841590\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4500985149115841590_11214155\"],
            [\"2013-08-09 18:58:14\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-5490447994355519724\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5490447994355519724_537384\"],
            [\"2013-08-09 18:58:15\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-6376764577995586077\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-6376764577995586077_421974\"],
            [\"2013-08-09 18:58:15\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-4820775562876973086\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4820775562876973086_2924594\"],
            [\"2013-08-09 18:58:15\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"6144100087183362600\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6144100087183362600_827000\"],
            [\"2013-08-09 18:58:15\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-2318015548037483618\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2318015548037483618_2169069\"],
            [\"2013-08-09 18:58:15\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"1108070585619463722\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_1108070585619463722_2480420\"],
            [\"2013-08-09 18:58:16\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"4497958792749582914\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_4497958792749582914_5021178\"],
            [\"2013-08-09 18:58:16\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-8534987575255152219\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-8534987575255152219_2621111\"],
            [\"2013-08-09 18:58:16\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"-7719409931013893232\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7719409931013893232_2203973\"],
            [\"2013-08-09 18:58:16\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"8416612258730348352\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8416612258730348352_11847065\"],
            [\"2013-08-09 18:58:17\", \"INFO\", \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", \"8841348180745215716\", \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8841348180745215716_10805348\"]
        ]
    },
    \"query-time\": \"1376041540441\"
}
"
        }
    )
)

(defn -main [& args]
    (web/start-jetty {:port 8080 :join? true}
        (web/handle-static-files {
            "/" "@/resources/index.html"
            "/index.html" "@/resources/index.html"
            "/css/bootstrap.css" "@/resources/css/bootstrap.css"
            "/css/index.css" "@/resources/css/index.css"
            "/css/jquery-ui.css" "@/resources/css/jquery-ui.css"
            "/css/jquery.loadmask.css" "@/resources/css/jquery.loadmask.css"
            "/image/bar.png" "@/resources/image/bar.png"
            "/image/go_btn.png" "@/resources/image/go_btn.png"
            "/image/icons_sprite.png" "@/resources/image/icons_sprite.png"
            "/image/loader.gif" "@/resources/image/loader.gif"
            "/image/logo.png" "@/resources/image/logo.png"
            "/image/search_bar.png" "@/resources/image/search_bar.png"
            "/image/shadow_soft.png" "@/resources/image/shadow_soft.png"
            "/image/splIcons.gif" "@/resources/image/splIcons.gif"
            "/image/sprite_button_icons.png" "@/resources/image/sprite_button_icons.png"
            "/js/bootstrap.js" "@/resources/js/bootstrap.js"
            "/js/bootstrap.min.js" "@/resources/js/bootstrap.min.js"
            "/js/highcharts.js" "@/resources/js/highcharts.js"
            "/js/jquery-1.10.2.min.js" "@/resources/js/jquery-1.10.2.min.js"
            "/js/jquery-ui.js" "@/resources/js/jquery-ui.js"
            "/js/jquery.loadmask.min.js" "@/resources/js/jquery.loadmask.min.js"
            "/js/jquery.pagination.js" "@/resources/js/jquery.pagination.js"
            "/js/log_monitor.js" "@/resources/js/log_monitor.js"
        })
        post-for-queryid
        get-for-updating
    )
)
