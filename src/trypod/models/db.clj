(ns trypod.models.db
  (:require [clojure.java.jdbc.deprecated :as sql])
  (:require [cemerick.friend [credentials :as creds]])
  (:import java.sql.DriverManager))

(def db {
         :subprotocol "mysql",
         :subname "//127.0.0.1:3306/trypod"
         :user "root"
         :password ""})


(defn read-users []
  (sql/with-connection
    db
    (sql/with-query-results res
      ["select * from user"]
      (doall res))))

(defn store-user [email password]
  (sql/with-connection
    db
    (sql/insert-values
     :user
     [:email :password]
     [email (creds/hash-bcrypt password)])
    ))
