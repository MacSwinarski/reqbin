(ns reqbin.test.web
  (:use [reqbin.web])
  (:use [clojure.test]))

(def req1 {:ssl-client-cert nil
           :remote-addr "0:0:0:0:0:0:0:1",
           :scheme :http,
           :query-params {},
           :session {},
           :form-params {},
           :multipart-params {},
           :request-method :get,
           :query-string nil,
           :content-type nil,
           :cookies {"ring-session" {:value "1ecb56fd-bd3b-44c6-b9bf-465a522f9565"}},
           :uri "/favicon.ico",
           :server-name "localhost",
           :params {},
           :headers {"accept-encoding" "gzip,deflate,sdch",
                     "connection" "keep-alive",
                     "user-agent" "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11",
                     "accept-language" "en-US,en;q=0.8, accept-charset ISO-8859-1,utf-8;q=0.7,*;q=0.3",
                     "accept" "*/*",
                     "host" "localhost:5000",
                     "cookie" "ring-session=1ecb56fd-bd3b-44c6-b9bf-465a522f9565"},
                     :content-length nil,
                     :server-port 5000,
                     :character-encoding nil,
                     :body "HttpInput org.eclipse.jetty.server.HttpInput@63cca2b0"})

;(deftest test-filter-request
;  (print (filter-request req1)))
;    ;is false "blabla"))


(deftest test-filter-empty-values-in-map1
  (is (= (filter-empty-values-in-map {}) {})))


(deftest test-filter-empty-values-in-map2
  (is (= (filter-empty-values-in-map {:foo "bar"}) {:foo "bar"})))


(deftest test-filter-empty-values-in-map3
  (is (= (filter-empty-values-in-map {:foo nil}) {})))