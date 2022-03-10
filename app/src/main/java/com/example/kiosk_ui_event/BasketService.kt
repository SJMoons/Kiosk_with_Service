package com.example.kiosk_ui_event

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment

class BasketService : Service(){
    var appendMenu = ArrayList<String>()
    var appendCupCount = ArrayList<String>()
    var appendTotalCost = ArrayList<String>()
    var toppingList = ArrayList<String>()
    var text: String = ""
    inner class MyBinder : Binder()  {
        fun getService() : BasketService {
            return this@BasketService
        }
    }
    val binder = MyBinder()

    override fun onBind(intent: Intent): IBinder? {
        return  binder
    }

//    fun clickMenuData(menuImage: String, menuName: String, menuCost: String) {
//        Log.d("mm","${menuCost}")
//
//    }

    fun basketMenuAdd(menu:String, cupCount:String, totalCost:String, toppingQuantity: ArrayList<String>, toppingLocationNum:ArrayList<Int>) {
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
        var list = arrayListOf<ArrayList<String>>(appendMenu,appendCupCount,appendTotalCost,toppingList)
        return list
    }

    fun menuListToBasket() :ArrayList<ArrayList<String>> {
        var list = arrayListOf<ArrayList<String>>(appendMenu,appendCupCount,appendTotalCost,toppingList)
        return list
    }



    fun payToBasket() :ArrayList<ArrayList<String>> {
        var list = arrayListOf<ArrayList<String>>(appendMenu,appendCupCount,appendTotalCost,toppingList)

        return list
    }

    fun payToComplete() : ArrayList<ArrayList<String>> {
        var list = arrayListOf<ArrayList<String>>(appendMenu,appendCupCount,appendTotalCost,toppingList)
        list[0].clear()
        list[1].clear()
        list[2].clear()
        list[3].clear()
        return list
    }
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//
//        return super.onStartCommand(intent, flags, startId)
//    }
    override fun onDestroy() {
        super.onDestroy()
    }
}