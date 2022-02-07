package com.example.kiosk_ui_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        //supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, StartFragment()).commit()
        //supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        //initEvent()
    }

    //fun initEvent() {
        //var togoBtn = findViewById<Button>(R.id.togoBtn)
        //var tostoreBtn = findViewById<Button>(R.id.togoBtn)

        //togoBtn!!.setOnClickListener{
        //    supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        //}
        //tostoreBtn!!.setOnClickListener{
         //   supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        //}
    //}
}