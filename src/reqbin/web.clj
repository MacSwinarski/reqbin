(ns reqbin.web
  (:use noir.core)
  (:require [noir.server :as server]))

(defn recorder
  "Request recorder"
  [handler]
  (fn [request]
    (let [resp (handler request)]
      (do
        (println "Request: " request)
        resp))))

(defpage [:any "*"] [] "Haha! You've been recorded!")

(defn -main [port]
  (do
    (server/add-middleware recorder)
    (server/start (Integer. port))))




