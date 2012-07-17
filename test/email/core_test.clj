(ns email.core-test
  (:use clojure.test
        [email.core :only [parse-from-string
                           parse-from-file
                           parse-from-directory]]))


(deftest parsing-message
    (let [message (parse-from-file "message")]
      (is (= (message :subject) "Re: This is a subject"))))


(deftest parsing-string
  (let [email-string (slurp "message")
        message (parse-from-string email-string)]
    (is (= (message :subject) "Re: This is a subject"))))
