package controllers

import (
	"encoding/json"
	"github.com/victorbej/webxxx-hackathon-moscow/internal/domain/models"
	"github.com/victorbej/webxxx-hackathon-moscow/pkg/utils"
	"net/http"
)

var CreateUserTable = func(w http.ResponseWriter, r *http.Request) {

	user := r.Context().Value("user").(uint) // берем id пользователя и отправляем запрос
	ut := &models.UserTable{}

	err := json.NewDecoder(r.Body).Decode(ut)
	if err != nil {
		utils.Respond(w, utils.Message(false, "Error while decoding request body"))
		return
	}

	ut.UserId = user
	resp := ut.Create()
	utils.Respond(w, resp)
}

var GetUserTablesFor = func(w http.ResponseWriter, r *http.Request) {
	id := r.Context().Value("user").(uint)
	data := models.GetUserTables(id)
	resp := utils.Message(true, "success")
	resp["data"] = data
	utils.Respond(w, resp)
}
