package com.example.kiosk_ui_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

//        initEvent()
//    }
//
//    fun initEvent() {
//        var completeBtn = findViewById<Button>(R.id.compelete_btn)
//
//        completeBtn!!.setOnClickListener{
//            toMenuListFragment()
//        }
//    }
//    fun toMenuListFragment() {
//        var fragment = ClickMenuFragment()
//        var myBundle = Bundle()
//    }
    override fun dataPass(menu:String,cupCount:Int,totalCost:String) {

    }
    }
}