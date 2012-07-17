(ns email.core
  (:require 
    [clojure.reflect :as r])
  (:use [clojure.contrib.java-utils :only [as-properties]]
    [clojure.java.io :only [input-stream file]]
    [clojure.pprint :only [print-table]])
  (:import 
    (javax.mail Session)
    (javax.mail.internet MimeMessage)
    (javax.mail.internet InternetHeaders)
  ))


(def session
  (Session/getDefaultInstance 
    (as-properties [["mail.store.protocol" "imaps"]])))


(defn fix-headers [m]
  (assoc-in m [:allHeaders]
        (map (fn [h] (assoc {} (.getName h) (.getValue h)))
            (enumeration-seq (:allHeaders m)))))


(defn get-from [message]
  (map (fn [m] (.toString m)) (:from message)))


(defn get-files [email-dir] 
  (rest (map (fn [f] (.toString f))
    (file-seq (file email-dir)))))


(defn get-bean-message [filename]
  (bean (MimeMessage. session (input-stream filename))))


(defn get-body [m] 
  (.getContent (.getBodyPart (:content m) 1)))


(defn transform-bean [bean-message]
    {:subject (:subject bean-message)
     :from (get-from bean-message)
     :body (get-body bean-message)})


;; Public functions -----------------------------------------------------------


(defn parse-from-string [email-string]
  (let [email-stream (java.io.ByteArrayInputStream. (.getBytes email-string))
        bean (bean (MimeMessage. session email-stream))]
    (transform-bean bean)))


(defn parse-from-file [filename]
    (transform-bean (get-bean-message filename)))
    

(defn parse-from-directory [email-dir]
  (map parse-from-file (get-files email-dir)))
