(ns metrics-server.api.hardware
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-metrics-with-http-info
  "Get hardware metrics"
  []
  (call-api "/hardware" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-metrics
  "Get hardware metrics"
  []
  (:data (get-metrics-with-http-info)))


; Убрать из выдачи элементы, у которых cpuTemp < 2
(defn task1a [metrics]
  (filter (fn [x] (< (get x :cpuTemp) 2)) metrics)
  )

; Убрать из выдачи элементы, у которых cpuTemp < 2
(defn task1b [metrics]
  (/
   (reduce + (map (fn [metric] (get metric :cpuTemp)) metrics))
   (count metrics)
   )
  )

; Убрать из выдачи элементы, у которых cpuTemp < 2
(defn task1c [metrics]
  (/
   (reduce + (map (fn [metric] (get metric :cpuLoad)) metrics))
   (count metrics)
   )
  )


(defn -main [& args]
  (println (task1a (get-metrics)))
  (println (task1b (get-metrics)))
  (println (task1c (get-metrics)))
  )