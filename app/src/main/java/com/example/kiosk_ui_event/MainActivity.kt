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
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.clickmenu_fragment.*
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.android.synthetic.main.start_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var language_code:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        DataBase(this,"Account.db",null,1)  //데어터베이스 class 객체 선언

        var korean = findViewById<RadioButton>(R.id.korean_btn)
        var english = findViewById<RadioButton>(R.id.english_btn)

        // 저장된 언어 코드를 불러온다.
        val sharedPreferences = getSharedPreferences(
            "Settings",
            Activity.MODE_PRIVATE
        )
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            Log.d("로그", "language :"+language)
            language_code = language
        }

        // 저장된 언어코드에 따라 라디오 버튼을 체크해준다.
        if(language_code.equals("ko") || language_code.equals("")){
            korean.setChecked(true);
        }else{
            english.setChecked(true);
        }

        //한국어 라디오 버튼 변경
        korean.setOnClickListener {
            setLocate("ko")
            recreate()
        }
        // 영어 라디오 버튼 변경
        english.setOnClickListener {
            setLocate("en")
            recreate()
        }
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

    fun foreGroundServiceStart(menuCount: Int, totalCost: Int) {
        val intent = Intent(this, ForegroundService::class.java)
            intent.putExtra("menuCount","${menuCount}")
            intent.putExtra("menuTotalCost","${totalCost}")
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

    fun payToCompleteActivity() {
        myService!!.payToComplete()
        payToCompleteReturn()
    }

    fun payToCompleteReturn() {
        var fragment = myService!!.payToComplete()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentArea, fragment).commit()
        var notification = Intent(this,ForegroundService::class.java)
        stopService(notification)
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
