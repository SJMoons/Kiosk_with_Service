package com.example.kiosk_ui_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.basket_fragment.*

class MainActivity : AppCompatActivity(), DataInterface {
    var appendMenu = ArrayList<String>()
    var appendCupCount = ArrayList<String>()
    var appendTotalCost = ArrayList<Int>()
    var toppingList = ArrayList<String>()
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

    //베스킷 데이터를 페이먼트 프래그먼트에 전달해주기 그리고 페이먼트 에서 메뉴 있나없나 확인하기

}
