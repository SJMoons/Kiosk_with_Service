package com.example.kiosk_ui_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.start_fragment.*

class MainActivity : AppCompatActivity(), DataInterface {
    var appendMenu = ArrayList<String>()
    var appendCupCount = ArrayList<String>()
    var appendTotalCost = ArrayList<Int>()
    var toppingList = ArrayList<String>()
    var text: String = ""

    var currentPosition=0

    //핸들러 설정
    //ui 변경하기
//    val handler= Handler(Looper.getMainLooper()){
//        setPage()
//        true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

//        val adapter=ViewPagerAdapter()
//        pager.adapter=adapter
//
//        //뷰페이저 넘기는 쓰레드
//        val thread=Thread(PagerRunnable())
//        thread.start()
    }

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



    override fun menuInformPass(menu:String, cupCount:String, totalCost:String, toppingQuantity: ArrayList<String>, toppingLocationNum:ArrayList<Int>, toppingName:ArrayList<String>) {
        var fragment = BasketFragment()
        var myBundle = Bundle()

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

        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
        myBundle.putStringArrayList("toppingList",toppingList)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    override fun clickMenuData(menuImage:String, menuName: String, menuCost: String) {
        var fragment = ClickMenuFragment()
        var myBundle = Bundle()
        myBundle.putString("menuImage", menuImage)
        myBundle.putString("menuName", menuName)
        myBundle.putString("menuCost", menuCost)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    override fun menuListToBasket() {
        var fragment = BasketFragment()
        var myBundle = Bundle()
        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
        myBundle.putStringArrayList("toppingList",toppingList)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    override fun payToBasket() {
        var fragment = BasketFragment()
        var myBundle = Bundle()
        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
        myBundle.putStringArrayList("toppingList",toppingList)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }
}
