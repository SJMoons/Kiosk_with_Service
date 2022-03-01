package com.example.kiosk_ui_event

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.nfc.FormatException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.clickmenu_fragment.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.start_fragment.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

    }

    var myService: BasketService? = null
    var isService = false
    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as BasketService.MyBinder
            myService = binder.getService()
            isService = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isService = false
        }
    }

    fun basketServiceStart() {
        val intent = Intent(this, BasketService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun foreGroundServiceStart(menuCount: Int) {
        val intent = Intent(this, ForegroundService::class.java)
            intent.putExtra("menuCount","${menuCount}")
            startService(intent)
    }

    fun clickMenuActivity(clickMenuImage: String, clickMenuName: String, clickMenuCost: String) {
        myService!!.clickMenuData(clickMenuImage, clickMenuName, clickMenuCost)
        clickMenuReturn(clickMenuImage, clickMenuName, clickMenuCost)
    }

    fun clickMenuReturn(clickMenuImage: String, clickMenuName: String, clickMenuCost: String) {
        var fragment = myService!!.clickMenuData(clickMenuImage, clickMenuName, clickMenuCost)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun basketMenuInformActivity(
        menu: String,
        cupCount: String,
        totalCost: String,
        toppingQuantity: ArrayList<String>,
        toppingLocationNum: ArrayList<Int>
    ) {
        myService!!.basketMenuAdd(menu, cupCount, totalCost, toppingQuantity, toppingLocationNum)
        basketMenuInformReturn()
    }

    fun basketMenuInformReturn() {
        var fragment = myService!!.basketMenuData(
        )
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun menuListtoBasketActivity() {
        myService!!.menuListToBasket()
        menuListtoBasketReturn()
    }

    fun menuListtoBasketReturn() {
        var fragment = myService!!.menuListToBasket()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun payToBasketActivity() {
        myService!!.payToBasket()
        payToBasketReturn()
    }

    fun payToBasketReturn() {
        var fragment = myService!!.payToBasket()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }
}

//    override fun menuInformPass(menu:String, cupCount:String, totalCost:String, toppingQuantity: ArrayList<String>, toppingLocationNum:ArrayList<Int>, toppingName:ArrayList<String>) {
//        var fragment = BasketFragment()
//        var myBundle = Bundle()
//
//        appendMenu.add(menu)
//        appendCupCount.add(cupCount)
//        appendTotalCost.add(totalCost.toInt())
//
//        var toppingArray = resources.getStringArray(R.array.topping_name)
//            for (i in toppingLocationNum) {
//                text += "${toppingArray[i]} X${toppingQuantity[i]}  "
//            }
//        toppingList.add(text)
//        Log.d("toppingList","${toppingList}")
//        text =""
//
//        myBundle.putStringArrayList("menu",appendMenu)
//        myBundle.putStringArrayList("cupCount",appendCupCount)
//        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
//        myBundle.putStringArrayList("toppingList",toppingList)
//        fragment.arguments = myBundle
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
//    }

//    override fun clickMenuData(menuImage:String, menuName: String, menuCost: String) {
//        var fragment = ClickMenuFragment()
//        var myBundle = Bundle()
//        myBundle.putString("menuImage", menuImage)
//        myBundle.putString("menuName", menuName)
//        myBundle.putString("menuCost", menuCost)
//        fragment.arguments = myBundle
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
//    }

//    override fun menuListToBasket() {
//        var fragment = BasketFragment()
//        var myBundle = Bundle()
//        myBundle.putStringArrayList("menu",appendMenu)
//        myBundle.putStringArrayList("cupCount",appendCupCount)
//        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
//        myBundle.putStringArrayList("toppingList",toppingList)
//        fragment.arguments = myBundle
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
//    }

//    override fun payToBasket() {
//        var fragment = BasketFragment()
//        var myBundle = Bundle()
//        myBundle.putStringArrayList("menu",appendMenu)
//        myBundle.putStringArrayList("cupCount",appendCupCount)
//        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
//        myBundle.putStringArrayList("toppingList",toppingList)
//        fragment.arguments = myBundle
//        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
//    }
//}




//    //페이지 변경하기
//    fun setPage(){
//        if(currentPosition==2) {
//            currentPosition = 0
//        }
//            pager.setCurrentItem(currentPosition,true)
//            currentPosition+=1
//
//    }
//
////    var startFragment = fragmentArea as StartFragment
//    //2초 마다 페이지 넘기기
//    inner class PagerRunnable:Runnable{
//        override fun run() {
//            while(true){
//                Thread.sleep(2000)
//                handler.sendEmptyMessage(0)
////                if (startFragment.startNum ==1) {
////                    break
////                }
//            }
//        }
//    }
