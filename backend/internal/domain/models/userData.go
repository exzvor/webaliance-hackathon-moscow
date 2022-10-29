package models

import (
	"fmt"
	"github.com/jinzhu/gorm"
	"github.com/victorbej/webxxx-hackathon-moscow/pkg/utils"
)

type UserTable struct {
	gorm.Model
	Location     string `json:"location"`
	Age          string `json:"age"`
	Balcony      bool   `json:"balcony"`
	Counter      int    `json:"counter"`
	CurrentFloor int    `json:"current_floor"`
	Floors       int    `json:"floors"`
	Subway       int    `json:"subway"`
	UserId       uint   `json:"user_id"` // пользователь, которому принадлежит таблица
}

// Метод Validate проверяет параметры и отправляет через http request body
func (ut *UserTable) Validate() (map[string]interface{}, bool) {

	if ut.Location == "" {
		return utils.Message(false, "location should be on the payload"), false
	}

	if ut.UserId <= 0 {
		return utils.Message(false, "User is not recognized"), false
	}

	//All the required parameters are present
	return utils.Message(true, "success"), true
}

func (ut *UserTable) Create() map[string]interface{} {

	if resp, ok := ut.Validate(); !ok {
		return resp
	}

	GetDB().Create(ut)

	resp := utils.Message(true, "success")
	resp["UserTable"] = ut
	return resp
}

func GetUserTable(id uint) *UserTable {

	ut := &UserTable{}
	err := GetDB().Table("user_tables").Where("id = ?", id).First(ut).Error
	if err != nil {
		return nil
	}
	return ut
}

func GetUserTables(user uint) []*UserTable {

	ut := make([]*UserTable, 0)
	err := GetDB().Table("user_tables").Where("user_id = ?", user).Find(&ut).Error
	if err != nil {
		fmt.Println(err)
		return nil
	}

	return ut
}
