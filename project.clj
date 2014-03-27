(defproject trypod "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://trypod.io"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [hiccup "1.0.5"]
                 [ring-server "0.3.1"]
                 [com.cemerick/friend "0.2.0"]
                 [org.clojure/java.jdbc "0.3.3"]
                 [mysql/mysql-connector-java "5.1.18"]
                 [migratus "0.7.0"]]
  :plugins [[lein-ring "0.8.10"]
            [migratus-lein "0.1.0"]]
  :ring {:handler trypod.handler/app
         :init trypod.handler/init
         :destroy trypod.handler/destroy}
  :migratus {:store :database
             :migration-dir "migrations"
             :db {:classname "com.mysql.jdbc.Driver"
                  :subprotocol "mysql"
                  :subname "//127.0.0.1:3306/trypod"
                  :user "root"
                  :password ""
                  :port "3306"}}
  :aot :all
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}}
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.2.1"]]}})
