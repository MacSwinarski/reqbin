(ns reqbin.dao
  (:use somnium.congomongo))

(defn hello []
  (print "Hello!"))

;locally run:
;export MONGOHQ_URL=`heroku config | grep MONGOHQ_URL | awk '{print $2;}'`
(defn heroku-mongo-url []
  (let [url (System/getenv "MONGOHQ_URL")]
    url))

(defmacro in-mongo [command]
  (let [url (heroku-mongo-url)
        conn (make-connection url)]
    (with-mongo conn
      command)))

(defn save! [coll-name data]
  (let [url (heroku-mongo-url)
        conn (make-connection url)]
    (with-mongo conn
      (insert! coll-name data))))

(defn find-filter [coll-name filter]
  (let [url (heroku-mongo-url)
        conn (make-connection url)]
    (with-mongo conn
      (fetch coll-name :where filter))))

(defn find-all [coll-name]
  (find-filter coll-name {}))

(defn -main []
  (do
    (save! :test1 {:a 2 :b "b" :c {:a "aa" :b "oo"}})
    (find-all :test2 )))
