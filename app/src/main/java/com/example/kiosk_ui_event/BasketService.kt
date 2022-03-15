package com.example.kiosk_ui_event

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment

class BasketService : Service(){
    var historyImage = ArrayList<String>()
    var historyMenu = ArrayList<String>()
    var historyCupCount = ArrayList<String>()
    var historyToppingList = ArrayList<String>()
    var historyTotalCost = ArrayList<String>()

    var appendImage = ArrayList<String>()
    var appendMenu = ArrayList<String>()
    var appendCupCount = ArrayList<String>()
    var appendTotalCost = ArrayList<String>()
    var toppingList = ArrayList<String>()
    var text: String = ""
    var id = ArrayList<String>()
    inner class MyBinder : Binder()  {
        fun getService() : BasketService {
            return this@BasketService
        }
    }
    val binder = MyBinder()

    override fun onBind(intent: Intent): IBinder {
        return  binder
    }

    fun idData(idValue:String){
        id.add(idValue)
    }

    fun startIdReturn(): String{
        return id[0]
    }

    fun basketMenuAdd(menuImage:String, menu:String, cupCount:String, totalCost:String, toppingQuantity: ArrayList<String>, toppingLocationNum:ArrayList<Int>) {
        appendImage.add(menuImage)
        appendMenu.add(menu)
        appendCupCount.add(cupCount)
        appendTotalCost.add(totalCost)
        var toppingArray = resources.getStringArray(R.array.topping_name)
        for (i in toppingLocationNum) {
            text += "${toppingArray[i]} X${toppingQuantity[i]}  "
        }
        toppingList.add(text)
        Log.d("toppingList","${toppingList}")
        text =""
    }

    fun basketMenuData():ArrayList<ArrayList<String>> {
        var list = arrayListOf<ArrayList<String>>(appendMenu,appendCupCount,appendTotalCost,toppingList,appendImage)
        return list
    }

    fun menuListToBasket() :ArrayList<ArrayList<String>> {
        var list = arrayListOf<ArrayList<String>>(appendMenu,appendCupCount,appendTotalCost,toppingList)
        return list
    }

    fun payToBasket() :ArrayList<ArrayList<String>> {
        var list = arrayListOf<ArrayList<String>>(appendMenu,appendCupCount,appendTotalCost,toppingList,appendImage)
        Log.d("image","$appendImage")
        return list
    }

    fun payToComplete() : ArrayList<ArrayList<String>> {
        var historyMenu = ArrayList<String>(appendMenu)
        Log.d("menu","$historyMenu")
        var historyImage = ArrayList<String>(appendImage)
        Log.d("menu","$historyImage")
//        historyCupCount = appendCupCount
//        historyToppingList = toppingList
//        historyTotalCost = appendTotalCost

        var list = arrayListOf<ArrayList<String>>(id,historyImage,historyMenu)
        appendMenu.clear()
        appendImage.clear()
        appendCupCount.clear()
        toppingList.clear()
        appendTotalCost.clear()
        return list
    }

    fun settingToStart(): String {
        return id[0]
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}