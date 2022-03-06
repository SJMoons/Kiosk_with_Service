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
    var appendTotalCost = ArrayList<Int>()
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

    fun clickMenuData(menuImage: String, menuName: String, menuCost: String) : Fragment {
        Log.d("mm","${menuCost}")
        var fragment = ClickMenuFragment()
        var myBundle = Bundle()
        myBundle.putString("menuImage", menuImage)
        myBundle.putString("menuName", menuName)
        myBundle.putString("menuCost", menuCost)
        fragment.arguments = myBundle

        return fragment
    }

    fun basketMenuAdd(menu:String, cupCount:String, totalCost:String, toppingQuantity: ArrayList<String>, toppingLocationNum:ArrayList<Int>) {
        appendMenu.add(menu)
        appendCupCount.add(cupCount)
        appendTotalCost.add(totalCost.toInt())
        var toppingArray = resources.getStringArray(R.array.topping_name)
        for (i in toppingLocationNum) {
            text += "${toppingArray[i]} X${toppingQuantity[i]}  "
        }
        toppingList.add(text)
        Log.d("toppingList","${toppingList}")
        text =""
    }

    fun basketMenuData() :Fragment {
        var fragment = BasketFragment()
        var myBundle = Bundle()

        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
        myBundle.putStringArrayList("toppingList",toppingList)
        fragment.arguments = myBundle

        return fragment
    }

    fun menuListToBasket() : Fragment {

        var fragment = BasketFragment()
        var myBundle = Bundle()
        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
        myBundle.putStringArrayList("toppingList",toppingList)
        fragment.arguments = myBundle
        return fragment
    }

    fun payToBasket() :Fragment {
        var fragment = BasketFragment()
        var myBundle = Bundle()
        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
        myBundle.putStringArrayList("toppingList",toppingList)
        fragment.arguments = myBundle

        return fragment
    }

    fun payToComplete() : Fragment {
        var fragment = StartFragment()
        var myBundle = Bundle()
        appendMenu.clear()
        appendCupCount.clear()
        appendTotalCost.clear()
        toppingList.clear()
        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
        myBundle.putStringArrayList("toppingList",toppingList)
        fragment.arguments = myBundle

        return fragment
    }
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//
//        return super.onStartCommand(intent, flags, startId)
//    }
    override fun onDestroy() {
        super.onDestroy()
    }
}