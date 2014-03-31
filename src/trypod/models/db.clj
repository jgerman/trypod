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
      ["select email as username, password from user"]
      (doall res))))

;returns a single user record
(defn find-user [username]
  (sql/with-connection
    db
    (sql/with-query-results res
      [(str "select id, email as username, password from user where email='" username "'")]
      (first (doall res)))))

(defn get-user-roles [user-id]
  "expects a usermap, the key :id must exist"
  (sql/with-connection
    db
    (sql/with-query-results res
      [(str "select role.name from user_roles, role where user_roles.role_id=role.id and user_roles.user_id=" user-id)]
      (doall res))))

(defn add-roles-to-usermap [usermap]
  "retrieves a users roles and associates them as a set of keywords under the key :roles to the usermap"
  (->> (get-user-roles (:id usermap))
      (map #(:name %))
      (map keyword)
      (assoc usermap :roles)
      ))

(defn get-user-map [username]
  (->> (find-user username)
      (add-roles-to-usermap)
      (assoc {} username)))

(defn store-user [email password]
  (sql/with-connection
    db
    (sql/insert-values
     :user
     [:email :password]
     [email (creds/hash-bcrypt password)])
    )
  )

(defn add-user-role [user-id role-id]
  (sql/with-connection
    db
    (sql/insert-values
     :user_roles
     [:user_id :role_id]
     [user-id role-id])))

(defn create-user [email password role-id]
  (-> (store-user email password)
      (:generated_key)
      (add-user-role role-id)))
