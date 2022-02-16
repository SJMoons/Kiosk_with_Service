package com.example.kiosk_ui_event

interface DataInterface {
    fun dataPass(menu:String,cupCount:String,totalCost:String)

    fun clickMenuData(menuImage:String,menuName:String,menuCost:String)
}