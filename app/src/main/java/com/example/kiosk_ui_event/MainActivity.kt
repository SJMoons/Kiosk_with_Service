package com.example.kiosk_ui_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), DataInterface {
    var appendMenu = ArrayList<String>()
    var appendCupCount = ArrayList<String>()
    var appendTotalCost = ArrayList<Int>()
//    var appendToppingQuantity = ArrayList<String>()
    //        var appendLocationNum = ArrayList<String>()
//    var appendToppingName = ArrayList<String>()
    var appendToppingQuantitySubList = ArrayList<String>()
//    var toppingSubList = ArrayList<String>()
    var list = ArrayList<String>()
    var text: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
    }

    override fun dataPass(menu:String, cupCount:String, totalCost:String, toppingQuantity: ArrayList<String>, toppingLocationNum:ArrayList<Int>, toppingName:ArrayList<String>) {

        var fragment = BasketFragment()
        var myBundle = Bundle()

        appendMenu.add(menu)
        appendCupCount.add(cupCount)
        appendTotalCost.add(totalCost.toInt())


        myBundle.putStringArrayList("menu",appendMenu)
        myBundle.putStringArrayList("cupCount",appendCupCount)
        myBundle.putIntegerArrayList("totalCost",appendTotalCost)
//        myBundle.putStringArrayList("originalToppingQuantity",toppingQuantity)

//        for (i in 0 until toppingLocationNum.count()) {        //toppingQuantity = [1 0 1 0 2]    toppingLocationNum = [0 2 4]   toppingName = ["한샷", "휘핑" ,"크림"]
//            appendToppingQuantity.add(toppingQuantity[toppingLocationNum[i]])
//            appendToppingQuantitySubList.add(toppingQuantity[toppingLocationNum[i]])
//            appendToppingName.add(toppingName[i])
//        }

        var toppingArray = resources.getStringArray(R.array.topping_name)
            for (i in toppingLocationNum) {
                text += "${toppingArray[i]} X${toppingQuantity[i]}  "
            }
        list.add(text)
        text =""

//        toppingSubList.add(appendToppingQuantitySubList.count().toString())       //서브 리스트 만들기

//        myBundle.putStringArrayList("toppingSubList",toppingSubList)
//        myBundle.putStringArrayList("toppingQuantity",appendToppingQuantity)
//        myBundle.putStringArrayList("toppingName",appendToppingName)
//        myBundle.putIntegerArrayList("toppingLocationNum",toppingLocationNum)
        myBundle.putStringArrayList("list",list)
        appendToppingQuantitySubList.clear()

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
}
