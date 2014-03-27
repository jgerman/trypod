(ns trypod.handler
  (:require [compojure.core :refer :all]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [trypod.routes.home :refer [home-routes]]
            [cemerick.friend :as friend]
            [cemerick.friend [workflows :as workflows]
             [credentials :as creds]]
            [hiccup.page :as h]
            [hiccup.element :as e]))


(def login-form
    [:div {:class "row"}
        [:div {:class "columns small-12"}
             [:h3 "Login"]
             [:div {:class "row"}
                   [:form {:method "POST" :action "login" :class "columns small-4"}
                          [:div "Username" [:input {:type "text" :name "username"}]]
                          [:div "Password" [:input {:type "password" :name "password"}]]
                          [:div [:input {:type "submit" :class "button" :value "Login"}]]]]]])

(def registration-page
  [:div
   [:h3 "Register"]
   [:div
    [:form {:method "POST" :action "register"}
     [:div "Email" [:input {:type "text" :name "email"}]]
     [:div "Password" [:input {:type "password" :name "password"}]]
     [:dev [:input {:type "submit" :class "button" :value "Register"}]]]]])

(def front-page
  [:div [:head
         [:title "Welcome to TryPod"]]
   [:body
    [:a {:href "/login"} "Login"]
    [:br]
    [:a {:href "/register"} "Register"]]])

(def users {"root" {:username "root"
                    :password (creds/hash-bcrypt "admin_password")
                    :roles #{::admin}}
            "jay" {:username "jay"
                   :password (creds/hash-bcrypt "user_password")
                   :roles #{::user}}
            })

(defn register-user [name pw]
  )
(defn init []
  (println "trypod is starting"))

(defn destroy []
  (println "trypod is shutting down"))

(defroutes app-routes
  (GET "/" req (h/html5 front-page))
  (GET "/home" req (friend/authorize #{::user}) "Hello World")
  (GET "/login" req (h/html5 login-form))
  (GET "/register" req (h/html5 registration-page))
  (POST "/register" [email password] (register-user email password))
  (route/resources "/" )
  (route/not-found "Not Found"))

(def app
  (handler/site
      (friend/authenticate
       app-routes
       {:allow-anon? true
        :login-uri "/login"
        :default-landing-uri "/"
        :credential-fn #(creds/bcrypt-credential-fn users %)
        :workflows [(workflows/interactive-form)]})

      ))
