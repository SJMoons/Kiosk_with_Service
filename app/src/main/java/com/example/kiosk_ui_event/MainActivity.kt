package com.example.kiosk_ui_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), DataInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
    }

    override fun dataPass(menu:String,cupCount:String,totalCost:String) {
        var fragment = BasketFragment()
        var myBundle = Bundle()
        myBundle.putString("menu",menu)
        myBundle.putString("cupCount",cupCount)
        myBundle.putString("totalCost",totalCost)
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
