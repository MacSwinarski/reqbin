(defproject reqbin "1.0.0-SNAPSHOT"
  :description "Request Bin"
  :dependencies [
                [org.clojure/clojure "1.4.0"]
                [ring/ring-jetty-adapter "1.1.0"]
                [noir "1.2.1"]
                  ]
  :main reqbin.web)