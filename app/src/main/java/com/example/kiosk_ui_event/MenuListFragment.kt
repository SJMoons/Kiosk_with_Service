package com.example.kiosk_ui_event

import android.app.Service
import android.content.Context
import android.content.ServiceConnection
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Dimension
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.login_fragment.*
import java.security.Provider

class MenuListFragment: Fragment() {
    lateinit var dataInterface: DataInterface

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        dataInterface = context as DataInterface
//    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.menulist_fragment, container,false)
        var mainActivity = activity as MainActivity
        mainActivity.basketServiceStart()
        buttonEvent(view)

        return view
    }



    fun buttonEvent(view:View) {
            var gobackBtn = view.findViewById<Button>(R.id.menu_goback_btn)
            var basketBtn = view.findViewById<Button>(R.id.basket_btn)
            var payBtn = view.findViewById<Button>(R.id.basket_pay_btn)
            var coffeeBtn = view.findViewById<Button>(R.id.coffeeBtn)
            var adeBtn = view.findViewById<Button>(R.id.adeBtn)
            var shackeBtn = view.findViewById<Button>(R.id.shakeBtn)

            var coffeeMenuArray = resources.getStringArray(R.array.menu_name_english)
            var coffeeCostArray = resources.getIntArray(R.array.menu_cost)
            var coffeeImageArray = arrayListOf<Int>(
                R.drawable.coffee1,
                R.drawable.coffee2,
                R.drawable.cafelatte,
                R.drawable.cafucino,
                R.drawable.einsupaner,
                R.drawable.yeonucafelatte
            )

            var adeMenuArray = resources.getStringArray(R.array.ade_name_english)
            var adeCostArray = resources.getIntArray(R.array.ade_cost)
            var adeImageArray = arrayListOf<Int>(
                R.drawable.chocolate,
                R.drawable.mintchocolate,
                R.drawable.topinutlatte,
                R.drawable.greentealatte,
                R.drawable.whitechocolate,
                R.drawable.gogumalatte
            )

            var shakeMenuArray = resources.getStringArray(R.array.shake_name_english)
            var shakeCostArray = resources.getIntArray(R.array.shake_cost)
            var shakeImageArray = arrayListOf<Int>(
                R.drawable.originshake,
                R.drawable.chococookieshake,
                R.drawable.strawberryshake
            )

            gobackBtn!!.setOnClickListener {
                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, StartFragment())
                    .commit()
            }
            basketBtn!!.setOnClickListener {
                var mainActivity = activity as MainActivity
                mainActivity.menuListtoBasketActivity()
//            dataInterface.menuListToBasket()
            }
            menuView(view, coffeeMenuArray, coffeeCostArray, coffeeImageArray)
            coffeeBtn!!.setOnClickListener {
                coffeeBtn.setTextColor((getResources().getColor(R.color.ediyacolor)))
                coffeeBtn.setBackgroundResource(R.drawable.whitebox_line)

                adeBtn.setTextColor((getResources().getColor(R.color.white)))
                adeBtn.setBackgroundResource(R.drawable.blue_box)

                shackeBtn.setTextColor((getResources().getColor(R.color.white)))
                shackeBtn.setBackgroundResource(R.drawable.blue_box)

                menuView(view, coffeeMenuArray, coffeeCostArray, coffeeImageArray)
            }
            adeBtn!!.setOnClickListener {
                adeBtn.setTextColor((getResources().getColor(R.color.ediyacolor)))  //getColor의 사용법이 바뀜, 용법이 바뀌었거나 사라질 용법
                adeBtn.setBackgroundResource(R.drawable.whitebox_line)

                coffeeBtn.setTextColor((resources.getColor(R.color.white)))
                coffeeBtn.setBackgroundResource(R.drawable.blue_box)

                shackeBtn.setTextColor((getResources().getColor(R.color.white)))
                shackeBtn.setBackgroundResource(R.drawable.blue_box)

                menuView(view, adeMenuArray, adeCostArray, adeImageArray)
            }
            shackeBtn!!.setOnClickListener {
                shackeBtn.setTextColor((getResources().getColor(R.color.ediyacolor)))
                shackeBtn.setBackgroundResource(R.drawable.whitebox_line)

                coffeeBtn.setTextColor((getResources().getColor(R.color.white)))
                coffeeBtn.setBackgroundResource(R.drawable.blue_box)

                adeBtn.setTextColor((getResources().getColor(R.color.white)))
                adeBtn.setBackgroundResource(R.drawable.blue_box)

                menuView(view, shakeMenuArray, shakeCostArray, shakeImageArray)
            }
            payBtn!!.setOnClickListener {
                var mainActivity = activity as MainActivity
                if (mainActivity.myService!!.appendMenu.isNotEmpty()) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentArea, PaymentFragment()).commit()
                }
            }
        }

    fun menuView(view:View,menuArray:kotlin.Array<String>,costArray:IntArray,imageArray:ArrayList<Int>) {
        var removeCoffeeMenu =  view.findViewById<LinearLayout>(R.id.menuLayout)
        removeCoffeeMenu.removeAllViews()
        var menuLayout = view.findViewById<LinearLayout>(R.id.menuLayout)
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var verticalParams = LinearLayout.LayoutParams(300, 400)
        for (index in 0 until menuArray.count()) {
            var horizontalLayout = LinearLayout(context)
            horizontalLayout.id = (index+2)*8
            horizontalLayout.orientation = LinearLayout.HORIZONTAL
            horizontalLayout.layoutParams = params
            horizontalLayout.weightSum = 2.0f
            menuLayout.addView(horizontalLayout)

            var verticalLayout = LinearLayout(context)
            verticalLayout.orientation = LinearLayout.VERTICAL
            verticalLayout.layoutParams = verticalParams
            horizontalLayout.addView(verticalLayout)

            var imageButton = ImageButton(context)
            imageButton.setImageResource(imageArray[index])
            imageButton.id = index
            imageButton.layoutParams = params
            imageButton.setPadding(50,20,0,0)
            verticalLayout.addView(imageButton)

            var verticalLayout2 = LinearLayout(context)
            verticalLayout2.orientation = LinearLayout.VERTICAL
            horizontalLayout.addView(verticalLayout2)

            var menu = TextView(context)
            menu.setTextColor((getResources().getColor(R.color.black)))
            menu.setText(menuArray[index])
            menu.layoutParams = params
            menu.setTextSize(Dimension.SP, 24.0f)
            menu.setPadding(50, 50, 0, 0)
            verticalLayout2.addView(menu)

            var cost = TextView(context)
            cost.setTextColor((getResources().getColor(R.color.ediyacolor)))
            cost.setText("₩ ${costArray[index]}")
            cost.setTextSize(Dimension.SP, 23.0f)
            cost.setPadding(50, 50, 0, 0)
            verticalLayout2.addView(cost)

//            var btn = view.findViewById<ImageButton>(imageButton.id)
            var btn =view.findViewById<LinearLayout>(horizontalLayout.id)
            var clickMenuImage = imageArray[index]
            var clickMenuName = menuArray[index]
            var clickMenuCost = costArray[index]
            Log.d("menu","${clickMenuImage}")

            btn!!.setOnClickListener{
                var mainActivity = activity as MainActivity
                mainActivity.clickMenuActivity("${clickMenuImage}","${clickMenuName}","${clickMenuCost}")
//                dataInterface.clickMenuData("${clickMenuImage}","${clickMenuName}","${clickMenuCost}")
            }
        }
    }
}


