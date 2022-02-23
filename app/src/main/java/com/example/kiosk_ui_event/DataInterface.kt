package com.example.kiosk_ui_event

interface DataInterface {
    fun dataPass(menu:String,cupCount:String,totalCost:String,toppingQuantity:ArrayList<String>,toppingLocationNum:ArrayList<Int>,toppingName:ArrayList<String>)

    fun clickMenuData(menuImage:String,menuName:String,menuCost:String)

    fun menuListToBasket()


}