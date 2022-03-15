package com.example.kiosk_ui_event

import android.app.Activity
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.nfc.FormatException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.clickmenu_fragment.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.setting_fragment.*
import kotlinx.android.synthetic.main.start_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentArea,LoginFragment()).commit()
        }

        Log.d("tag",MyApp.prefs.setLanguage)

        if (MyApp.prefs.setLanguage == "ko") {

            language("ko")
        } else if (MyApp.prefs.setLanguage == "en") {
            language("en")
        }

    }

    fun language(language:String) {
        val config = resources.configuration
        val locale = Locale(language)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            createConfigurationContext(config)
        }
        resources.updateConfiguration(config,resources.displayMetrics)
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

    fun shortToastShow(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()

    }

    fun basketServiceStart() {
        val intent = Intent(this, BasketService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun foreGroundServiceStart(menuCount: Int, totalCost: Int) {
        val intent = Intent(this, ForegroundService::class.java)
            intent.putExtra("menuCount","${menuCount}")
            intent.putExtra("menuTotalCost","${totalCost}")
            startService(intent)
    }

    fun idInform(idValue:String) {
        Log.d("idd","$idValue")
        myService?.idData(idValue)
        var fragment = StartFragment()
        var myBundle = Bundle()
        myBundle.putString("id", idValue)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun menuToStartIdData() {
        var idReturn = myService!!.startIdReturn()
        var fragment = StartFragment()
        var myBundle = Bundle()
        myBundle.putString("id", idReturn)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun clickMenuActivity(clickMenuImage: String, clickMenuName: String, clickMenuCost: String) {
        var fragment = ClickMenuFragment()
        var myBundle = Bundle()
        myBundle.putString("menuImage", clickMenuImage)
        myBundle.putString("menuName", clickMenuName)
        myBundle.putString("menuCost", clickMenuCost)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun basketMenuInformActivity(
        menuImage: String,
        menu: String,
        cupCount: String,
        totalCost: String,
        toppingQuantity: ArrayList<String>,
        toppingLocationNum: ArrayList<Int>
    ) {
        myService!!.basketMenuAdd(menuImage, menu, cupCount, totalCost, toppingQuantity, toppingLocationNum)
        basketMenuInformReturn()
    }

    fun basketMenuInformReturn() {
        var fragment = BasketFragment()
        var list = myService!!.basketMenuData()
        var myBundle = Bundle()
        myBundle.putStringArrayList("menu",list[0])
        myBundle.putStringArrayList("cupCount",list[1])
        myBundle.putStringArrayList("totalCost",list[2])
        myBundle.putStringArrayList("toppingList",list[3])
        myBundle.putStringArrayList("menuImage",list[4])
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun menuListtoBasketActivity() {
        var fragment = BasketFragment()
        var list = myService!!.menuListToBasket()
        var myBundle = Bundle()
        myBundle.putStringArrayList("menu",list[0])
        myBundle.putStringArrayList("cupCount",list[1])
        myBundle.putStringArrayList("totalCost",list[2])
        myBundle.putStringArrayList("toppingList",list[3])
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }


    fun payToBasketActivity() {
        var fragment = BasketFragment()
        var list = myService!!.payToBasket()
        var myBundle = Bundle()
        myBundle.putStringArrayList("menu",list[0])
        myBundle.putStringArrayList("cupCount",list[1])
        myBundle.putStringArrayList("totalCost",list[2])
        myBundle.putStringArrayList("toppingList",list[3])
        myBundle.putStringArrayList("menuImage",list[4])
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    fun payToCompleteActivity() {
        val db = DataBase(this,"Account.db",null,1)  //데어터베이스 class 객체 선언
        val dbControl = DatabaseControl()
        val writeableDb = db.writableDatabase

        var list = myService!!.payToComplete()
        Log.d("ss","${list}")
        for (index in 0 until list[1].size) {
            var data = arrayListOf<String>(list[0][0],list[1][index],list[2][index])

            var idHistoryColumn = arrayListOf<String>("id","menuimage","menu")
            dbControl.create(writeableDb,"idhistory_table",idHistoryColumn,data)
        }

        var fragment = StartFragment()
        var myBundle = Bundle()
        myBundle.putString("id",list[0][0])
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
        var notification = Intent(this,ForegroundService::class.java)
        stopService(notification)
    }

    fun settingToStart() {
        var fragment = StartFragment()
        var id = myService!!.settingToStart()
        var myBundle = Bundle()
        myBundle.putString("id",id)
        fragment.arguments = myBundle
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        var notification = Intent(this,ForegroundService::class.java)
        stopService(notification)
    }


    //Locale 객체를 생성특정 지리적, 정치적 또는 문화적 영역을 나타냅니다.
    private fun setLocate(Lang: String) {
        Log.d("로그", "setLocate")
        val locale = Locale(Lang) // Local 객체 생성. 인자로는 해당 언어의 축약어가 들어가게 됩니다. (ex. ko, en)
        Locale.setDefault(locale) // 생성한 Locale로 설정을 해줍니다.

        val config = Configuration() //이 클래스는 응용 프로그램이 검색하는 리소스에 영향을 줄 수 있는
        // 모든 장치 구성 정보를 설명합니다.

        config.setLocale(locale) // 현재 유저가 선호하는 언어를 환경 설정으로 맞춰 줍니다.
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        // Shared에 현재 언어 상태를 저장해 줍니다.
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

}
