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
        (prn req)
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
        \"time-series\": [\"2013-08-08 00:00:00\", \"2013-08-08 00:01:00\", \"2013-08-08 00:02:00\", \"2013-08-08 00:03:00\", \"2013-08-08 00:04:00\", \"2013-08-08 00:05:00\", \"2013-08-08 00:06:00\", \"2013-08-08 00:07:00\", \"2013-08-08 00:08:00\", \"2013-08-08 00:09:00\", \"2013-08-08 00:10:00\", \"2013-08-08 00:11:00\", \"2013-08-08 00:12:00\", \"2013-08-08 00:13:00\", \"2013-08-08 00:14:00\", \"2013-08-08 00:15:00\", \"2013-08-08 00:16:00\", \"2013-08-08 00:17:00\", \"2013-08-08 00:18:00\", \"2013-08-08 00:19:00\", \"2013-08-08 00:20:00\", \"2013-08-08 00:21:00\", \"2013-08-08 00:22:00\", \"2013-08-08 00:23:00\", \"2013-08-08 00:24:00\", \"2013-08-08 00:25:00\", \"2013-08-08 00:26:00\", \"2013-08-08 00:27:00\", \"2013-08-08 00:28:00\", \"2013-08-08 00:29:00\"],
        \"search-count\": [75, 81, 91, 71, 61, 9, 89, 69, 93, 36, 74, 47, 12, 12, 41, 90, 49, 55, 60, 10, 9, 25, 16, 11, 28, 40, 71, 44, 9, 80]
    },
    \"groupall\": [
        {
            \"count_id_1\": 200, 
            \"gKeys\": {
                \"level\": null
            }
        }
    ], 
    \"grouptable\": [
        {
            \"count_id_1\": 2, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:56:00\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 4, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:56:20\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 19, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:56:40\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 14, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:56:50\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 11, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:57:00\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 31, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:57:10\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 5, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:57:20\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 10, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:57:30\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 18, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:57:40\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 28, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:57:50\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 5, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:58:00\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 27, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:58:10\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 14, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:58:20\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 6, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:58:30\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 1, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:58:40\", 
                \"level\": null
            }
        }, 
        {
            \"count_id_1\": 5, 
            \"gKeys\": {
                \"groupTime\": \"08/08/2013 18:58:50\", 
                \"level\": null
            }
        }
    ], 
    \"logtable\": [
        {
            \"id_1\": \"9196250142345063344\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_9196250142345063344_5277466\", 
            \"timestamp\": \"08/08/2013 18:57:40\"
        }, 
        {
            \"id_1\": \"-2293429873557112819\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2293429873557112819_3614594\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"-1970220620268133764\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-1970220620268133764_2464569\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"2209152077006569192\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_2209152077006569192_2165825\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"-9032564916765756352\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-9032564916765756352_544146\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"1584592962350205741\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_1584592962350205741_3424548\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"-3500705922293771948\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-3500705922293771948_537720\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"7748586760585967980\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7748586760585967980_5246522\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"2257423460492806845\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_2257423460492806845_11147352\", 
            \"timestamp\": \"08/08/2013 18:57:43\"
        }, 
        {
            \"id_1\": \"-3974105409641541931\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-3974105409641541931_11472021\", 
            \"timestamp\": \"08/08/2013 18:57:45\"
        }, 
        {
            \"id_1\": \"993877546571983183\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_993877546571983183_2482450\", 
            \"timestamp\": \"08/08/2013 18:57:45\"
        }, 
        {
            \"id_1\": \"6167147539767587327\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6167147539767587327_11488401\", 
            \"timestamp\": \"08/08/2013 18:57:47\"
        }, 
        {
            \"id_1\": \"-7686243307053460358\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7686243307053460358_3458057\", 
            \"timestamp\": \"08/08/2013 18:57:48\"
        }, 
        {
            \"id_1\": \"-7608766485581267526\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7608766485581267526_673460\", 
            \"timestamp\": \"08/08/2013 18:57:48\"
        }, 
        {
            \"id_1\": \"-4766643123015059895\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4766643123015059895_1078942\", 
            \"timestamp\": \"08/08/2013 18:57:50\"
        }, 
        {
            \"id_1\": \"-5663234269452667849\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5663234269452667849_2480975\", 
            \"timestamp\": \"08/08/2013 18:57:50\"
        }, 
        {
            \"id_1\": \"381196251613344840\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_381196251613344840_612342\", 
            \"timestamp\": \"08/08/2013 18:57:50\"
        }, 
        {
            \"id_1\": \"577071493286575335\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_577071493286575335_11492776\", 
            \"timestamp\": \"08/08/2013 18:57:51\"
        }, 
        {
            \"id_1\": \"-4366800936733136497\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4366800936733136497_5283316\", 
            \"timestamp\": \"08/08/2013 18:57:51\"
        }, 
        {
            \"id_1\": \"6276506500660167231\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6276506500660167231_398775\", 
            \"timestamp\": \"08/08/2013 18:57:52\"
        }, 
        {
            \"id_1\": \"-5500941694965839445\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5500941694965839445_2480214\", 
            \"timestamp\": \"08/08/2013 18:57:52\"
        }, 
        {
            \"id_1\": \"6656406666893049435\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6656406666893049435_2128292\", 
            \"timestamp\": \"08/08/2013 18:57:54\"
        }, 
        {
            \"id_1\": \"-5431553043909960947\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5431553043909960947_11205595\", 
            \"timestamp\": \"08/08/2013 18:57:54\"
        }, 
        {
            \"id_1\": \"-7418752015773360736\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7418752015773360736_4712912\", 
            \"timestamp\": \"08/08/2013 18:57:54\"
        }, 
        {
            \"id_1\": \"7455678178861538954\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7455678178861538954_429938\", 
            \"timestamp\": \"08/08/2013 18:57:54\"
        }, 
        {
            \"id_1\": \"-2123995176868425694\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2123995176868425694_3515277\", 
            \"timestamp\": \"08/08/2013 18:57:54\"
        }, 
        {
            \"id_1\": \"2330747764478432061\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_2330747764478432061_11119276\", 
            \"timestamp\": \"08/08/2013 18:57:55\"
        }, 
        {
            \"id_1\": \"6904988708838685895\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6904988708838685895_5376673\", 
            \"timestamp\": \"08/08/2013 18:57:55\"
        }, 
        {
            \"id_1\": \"-4435719614067843117\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4435719614067843117_10388076\", 
            \"timestamp\": \"08/08/2013 18:57:55\"
        }, 
        {
            \"id_1\": \"4278657071388830776\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_4278657071388830776_2929570\", 
            \"timestamp\": \"08/08/2013 18:57:55\"
        }, 
        {
            \"id_1\": \"-9205761362901501641\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-9205761362901501641_10615287\", 
            \"timestamp\": \"08/08/2013 18:57:55\"
        }, 
        {
            \"id_1\": \"8291783686731032923\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8291783686731032923_11474279\", 
            \"timestamp\": \"08/08/2013 18:57:56\"
        }, 
        {
            \"id_1\": \"4843875551018425615\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_4843875551018425615_11850532\", 
            \"timestamp\": \"08/08/2013 18:57:56\"
        }, 
        {
            \"id_1\": \"5002500616427251683\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_5002500616427251683_4767964\", 
            \"timestamp\": \"08/08/2013 18:57:57\"
        }, 
        {
            \"id_1\": \"8465959605068896725\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8465959605068896725_607996\", 
            \"timestamp\": \"08/08/2013 18:57:58\"
        }, 
        {
            \"id_1\": \"-2943287808487214523\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2943287808487214523_2583770\", 
            \"timestamp\": \"08/08/2013 18:57:58\"
        }, 
        {
            \"id_1\": \"96217341267629501\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_96217341267629501_5003029\", 
            \"timestamp\": \"08/08/2013 18:57:58\"
        }, 
        {
            \"id_1\": \"-7115266324321469383\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7115266324321469383_2343469\", 
            \"timestamp\": \"08/08/2013 18:57:58\"
        }, 
        {
            \"id_1\": \"-695458870783336477\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-695458870783336477_10648289\", 
            \"timestamp\": \"08/08/2013 18:57:58\"
        }, 
        {
            \"id_1\": \"9034437279673059450\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_9034437279673059450_2312943\", 
            \"timestamp\": \"08/08/2013 18:57:58\"
        }, 
        {
            \"id_1\": \"1597830234837264456\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_1597830234837264456_2167989\", 
            \"timestamp\": \"08/08/2013 18:57:58\"
        }, 
        {
            \"id_1\": \"7116995168261300326\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7116995168261300326_312688\", 
            \"timestamp\": \"08/08/2013 18:57:59\"
        }, 
        {
            \"id_1\": \"5325532776085505375\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_5325532776085505375_133305\", 
            \"timestamp\": \"08/08/2013 18:58:00\"
        }, 
        {
            \"id_1\": \"9166888087053030288\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_9166888087053030288_448220\", 
            \"timestamp\": \"08/08/2013 18:58:06\"
        }, 
        {
            \"id_1\": \"-9115951738129844453\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-9115951738129844453_2762506\", 
            \"timestamp\": \"08/08/2013 18:58:06\"
        }, 
        {
            \"id_1\": \"-8159745059722194250\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-8159745059722194250_486478\", 
            \"timestamp\": \"08/08/2013 18:58:07\"
        }, 
        {
            \"id_1\": \"7395431284604724127\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_7395431284604724127_11850648\", 
            \"timestamp\": \"08/08/2013 18:58:07\"
        }, 
        {
            \"id_1\": \"5646409331255298552\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_5646409331255298552_398689\", 
            \"timestamp\": \"08/08/2013 18:58:10\"
        }, 
        {
            \"id_1\": \"3326201913404078457\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_3326201913404078457_5196651\", 
            \"timestamp\": \"08/08/2013 18:58:10\"
        }, 
        {
            \"id_1\": \"6711612843401554204\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6711612843401554204_2198766\", 
            \"timestamp\": \"08/08/2013 18:58:10\"
        }, 
        {
            \"id_1\": \"8491413143793969289\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8491413143793969289_3614360\", 
            \"timestamp\": \"08/08/2013 18:58:13\"
        }, 
        {
            \"id_1\": \"-4500985149115841590\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4500985149115841590_11214155\", 
            \"timestamp\": \"08/08/2013 18:58:14\"
        }, 
        {
            \"id_1\": \"-5490447994355519724\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-5490447994355519724_537384\", 
            \"timestamp\": \"08/08/2013 18:58:14\"
        }, 
        {
            \"id_1\": \"-6376764577995586077\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-6376764577995586077_421974\", 
            \"timestamp\": \"08/08/2013 18:58:15\"
        }, 
        {
            \"id_1\": \"-4820775562876973086\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-4820775562876973086_2924594\", 
            \"timestamp\": \"08/08/2013 18:58:15\"
        }, 
        {
            \"id_1\": \"6144100087183362600\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_6144100087183362600_827000\", 
            \"timestamp\": \"08/08/2013 18:58:15\"
        }, 
        {
            \"id_1\": \"-2318015548037483618\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-2318015548037483618_2169069\", 
            \"timestamp\": \"08/08/2013 18:58:15\"
        }, 
        {
            \"id_1\": \"1108070585619463722\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_1108070585619463722_2480420\", 
            \"timestamp\": \"08/08/2013 18:58:15\"
        }, 
        {
            \"id_1\": \"4497958792749582914\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_4497958792749582914_5021178\", 
            \"timestamp\": \"08/08/2013 18:58:16\"
        }, 
        {
            \"id_1\": \"-8534987575255152219\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-8534987575255152219_2621111\", 
            \"timestamp\": \"08/08/2013 18:58:16\"
        }, 
        {
            \"id_1\": \"-7719409931013893232\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_-7719409931013893232_2203973\", 
            \"timestamp\": \"08/08/2013 18:58:16\"
        }, 
        {
            \"id_1\": \"8416612258730348352\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8416612258730348352_11847065\", 
            \"timestamp\": \"08/08/2013 18:58:16\"
        }, 
        {
            \"id_1\": \"8841348180745215716\", 
            \"level\": \"INFO\", 
            \"location\": \"org.apache.hadoop.hdfs.server.datanode.BlockPoolSliceScanner\", 
            \"message\": \"Verification succeeded for BP-618800880-127.0.1.1-1340026760932:blk_8841348180745215716_10805348\", 
            \"timestamp\": \"08/08/2013 18:58:17\"
        }
    ], 
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
