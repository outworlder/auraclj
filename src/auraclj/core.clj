(ns auraclj.core
  (:gen-class)
  (:require [clj-http.client :as client]
            [clojure.data.xml :as xml]
            [clojure.zip :as zip])
  (:use [clojure.data.zip.xml])
  (:import [java.io ByteArrayInputStream]))

;; API Endpoints
(def marketstat "http://api.eve-central.com/api/marketstat")


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;;; Getting around Clojure's braindead XML parser
(defn string-to-stream [string]
  (ByteArrayInputStream.
   (.getBytes (.trim string))))

(defn get-marketstat
  "Retrieves data from the marketstat endpoint"
  [typeid]
  (let [return (client/get marketstat { :query-params { :typeid typeid } })]
    (xml/parse (string-to-stream (:body return)))))

(defn parse-marketstat
  [data]
  (zip/xml-zip data))

;;; Parsing
;;; http://stackoverflow.com/questions/1194044/clojure-xml-parsing?rq=1
;;; (xml-> zipped :marketstat :type (attr :id))