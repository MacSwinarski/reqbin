(ns reqbin.web
  (:use noir.core)
  (:require [noir.server :as server])
  (:use somnium.congomongo)
  (:use hiccup.page-helpers)
  (:use ring.middleware.file)
  (:require [reqbin.dao :as dao])
  (:require [clojure.data.json :as json]))


(defn filter-empty-values-in-map [m]
  ;(filter #(not (nil? %)) m))
  m)

(defn filter-request [request]
  (let [nobody-req (dissoc request :body)
        nonil-req (filter #(not (nil? %)) nobody-req)]
    (into {} nonil-req)))

(defn filter-data [data]
  (map #(dissoc % :_id) (filter #(not (nil? %)) data)))

(defn record-req?
  "Should the request be recorded"
  [req]
  (let [uri (:uri req)
        recorded? (.startsWith uri "/record")]
    (do
      (println "uri: " uri "recorded: " recorded?)
      true)))

(defn recorder
  "Request recorder"
  [handler]
  (fn [request]
    (let [resp (handler request)
          filtered-request (filter-request request)]
      (if (record-req? request)
        (do
          (println "Request: " request)
          (println "Filtered request: " filtered-request)
          (dao/save! :test2 filtered-request)))
        resp)))

(defpartial layout [& content]
  (html5
    [:head
     [:title "Request Bin"]
     (include-css "/css/style.css")
     ]
    [:body
     [:div#content
      content]]))


(defpartial request-row [request]
  [:div.req-row
    [:div.req-cell (:scheme request)]
    [:div.req-cell (:request-method request)]
    [:div.req-cell (:query-params request)]
    [:div.req-cell (:session request)]
    [:div.req-cell (:form-params  request)]
    [:div.req-cell (:multipart-params  request)]
    [:div.req-cell (:query-string request)]
    [:div.req-cell (:content-type request)]
    [:div.req-cell (:cookies request)]
    [:div.req-cell (:server-name request)]
    [:div.req-cell (:params request)]
    [:div.req-cell (:headers request)]
    [:div.req-cell (:content-length request)]
    [:div.req-cell (:server-port request)]
    [:div.req-cell (:character-encoding request)]
    ])

(defpartial request-table [requests]
  [:div.req-table
   (map request-row requests)])

(defpage [:get "/"] [] "Request recorder bin. Send any request to /record/ and view it on /view.")

(defpage [:get "/view"] [] (layout (request-table (filter-data (dao/find-all :test2)))))

(defpage [:any "/record/*"] []
  (str "Haha! You've been recorded!\n"))


(defn -main [port]
  (do
    (server/add-middleware wrap-file "resources/public")
    (server/add-middleware recorder)
    (server/start (Integer. port))))




