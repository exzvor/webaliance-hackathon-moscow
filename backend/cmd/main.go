package main

import (
	"fmt"
	"github.com/gorilla/mux"
	"github.com/victorbej/webxxx-hackathon-moscow/internal/domain/app"
	"github.com/victorbej/webxxx-hackathon-moscow/internal/domain/controllers"
	"net/http"
	"os"
)

func main() {
	port := os.Getenv("PORT")
	router := mux.NewRouter()

	router.HandleFunc("/api/user/new", controllers.CreateAccount).Methods("POST")
	router.HandleFunc("/api/user/login", controllers.Authenticate).Methods("POST")
	router.HandleFunc("/api/table/new", controllers.CreateUserTable).Methods("POST")
	router.HandleFunc("/api/me/tables", controllers.GetUserTablesFor).Methods("GET")

	router.Use(app.JwtAuthentication) // добавляем jwt с помощью middleware

	//router.NotFoundHandler = app.NotFoundHandler

	err := http.ListenAndServe(":"+port, router) // запускаем приложение, посещаем https://hack-auth.herokuapp.com/api
	if err != nil {
		fmt.Print(err)
	}
}
