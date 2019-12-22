(ns metrics-server.api.files
  (:require [metrics-server.core :refer [call-api check-required-params with-collection-format]])
  (:import (java.io File)))

(defn get-files-with-http-info
  "Get files in directory on server"
  []
  (call-api "/files" :get
            {:path-params   {}
             :header-params {}
             :query-params  {}
             :form-params   {}
             :content-types []
             :accepts       []
             :auth-names    []}))

(defn get-files
  "Get files in directory on server"
  []
  (:data (get-files-with-http-info)))


; Убрать из выдачи директории
(defn task2a [files]
  (filter (fn [x] (not (get x :directory))) files)
  )

; Отображение только исполняемого файла
(defn task2b [files]
  (filter (fn [x] (get x :executable)) files)
  )

(defn conftocfg [filename]
  (clojure.string/replace filename #".conf" ".cfg")
  )

; Изменение расширения файла из conf в cfg
(defn task2c [files]
  (map (fn [file]
         {
           :name (conftocfg (get file :name))
           :size (get file :size)
           :changed (get file :changed)
           :directory (get file :directory)
           :executable (get file :executable)
           }
         ) files)
  )

; Ввывод среднего размера файла
(defn task2d [files]
  (/
   (reduce + (map (fn [file] (get file :size)) (filter (fn [x] (not (get x :directory))) files)))
   (count (filter (fn [x] (not (get x :directory))) files))
   )
  )

(defn -main [& args]
      (println (task2a (get-files)))
      (println (task2b (get-files)))
      (println (task2c (get-files)))
      (println (task2d (get-files)))
  )