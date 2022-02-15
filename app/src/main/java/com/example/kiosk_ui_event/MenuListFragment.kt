package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Dimension
import androidx.fragment.app.Fragment

class MenuListFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.menulist_fragment, container,false)
        var gobackBtn = view.findViewById<Button>(R.id.menu_goback_btn)
        var basketBtn = view.findViewById<Button>(R.id.basket_btn)
        var payBtn = view.findViewById<Button>(R.id.basket_pay_btn)
        var coffeeBtn = view.findViewById<Button>(R.id.coffeeBtn)
        var adeBtn = view.findViewById<Button>(R.id.adeBtn)
        var shackeBtn = view.findViewById<Button>(R.id.shakeBtn)
//        var removeCoffeeMenu =  view.findViewById<LinearLayout>(R.id.menuLayout)

//        var btnColorChange = coffeeBtn.setTextColor((getResources().getColor(R.color.white)

//        for (i in 0..5) {
//            var imageBtn = view.findViewById<ImageButton>(R.id.imageBtn1+i)
//            imageBtn!!.setOnClickListener{
//                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, ClickMenuFragment()).commit()
//            }
//        }
        gobackBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, StartFragment()).commit()
        }
        basketBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, BasketFragment()).commit()
        }
        coffeeBtn!!.setOnClickListener{
            coffeeBtn.setTextColor((getResources().getColor(R.color.ediyacolor)))
            coffeeBtn.setBackgroundResource(R.drawable.whitebox_line)

            adeBtn.setTextColor((getResources().getColor(R.color.white)))
            adeBtn.setBackgroundResource(R.drawable.blue_box)

            shackeBtn.setTextColor((getResources().getColor(R.color.white)))
            shackeBtn.setBackgroundResource(R.drawable.blue_box)

            var menuArray = resources.getStringArray(R.array.menu_name)
            var costArray = resources.getIntArray(R.array.menu_cost)
            var imageArray = resources.getIntArray(R.array.image_array)
            menu(view,menuArray,costArray,imageArray)
        }
        adeBtn!!.setOnClickListener{
            adeBtn.setTextColor((getResources().getColor(R.color.ediyacolor)))  //getColor의 사용법이 바뀜, 용법이 바뀌었거나 사라질 용법
            adeBtn.setBackgroundResource(R.drawable.whitebox_line)

            coffeeBtn.setTextColor((resources.getColor(R.color.white)))
            coffeeBtn.setBackgroundResource(R.drawable.blue_box)

            shackeBtn.setTextColor((getResources().getColor(R.color.white)))
            shackeBtn.setBackgroundResource(R.drawable.blue_box)

            var adeMenuArray = resources.getStringArray(R.array.ade_name)
            var adeCostArray = resources.getIntArray(R.array.ade_cost)
            var adeImageArray = resources.getIntArray(R.array.ade_image_array)
            menu(view,adeMenuArray,adeCostArray,adeImageArray)
        }
        shackeBtn!!.setOnClickListener{
            shackeBtn.setTextColor((getResources().getColor(R.color.ediyacolor)))
            shackeBtn.setBackgroundResource(R.drawable.whitebox_line)

            coffeeBtn.setTextColor((getResources().getColor(R.color.white)))
            coffeeBtn.setBackgroundResource(R.drawable.blue_box)

            adeBtn.setTextColor((getResources().getColor(R.color.white)))
            adeBtn.setBackgroundResource(R.drawable.blue_box)

            var shakeMenuArray = resources.getStringArray(R.array.shake_name)
            var shakeCostArray = resources.getIntArray(R.array.shake_cost)
            var shakeImageArray = resources.getIntArray(R.array.shake_image_array)
            menu(view,shakeMenuArray,shakeCostArray,shakeImageArray)

        }
        payBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, PaymentFragment()).commit()
        }

        return view
    }

    fun menu(view:View,menuArray:kotlin.Array<String>,costArray:IntArray,imageArray:IntArray) {
        var removeCoffeeMenu =  view.findViewById<LinearLayout>(R.id.menuLayout)
        removeCoffeeMenu.removeAllViews()
        var menuLayout = view.findViewById<LinearLayout>(R.id.menuLayout)

        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var verticalParams = LinearLayout.LayoutParams(300, 400)
        var menuParams = LinearLayout.LayoutParams(400,200)
        for (index in 0 until menuArray.count()) {
            var horizontalLayout = LinearLayout(context)
            horizontalLayout.orientation = LinearLayout.HORIZONTAL
            horizontalLayout.layoutParams = params
            horizontalLayout.weightSum = 2.0f
            menuLayout.addView(horizontalLayout)

            var verticalLayout = LinearLayout(context)
            verticalLayout.orientation = LinearLayout.VERTICAL
            verticalLayout.layoutParams = verticalParams
            horizontalLayout.addView(verticalLayout)

            var imageButton = ImageButton(context)
            imageButton.id = index
            imageButton.layoutParams = verticalParams
            imageButton.setBackgroundResource(imageArray[index])
            verticalLayout.addView(imageButton)

            var verticalLayout2 = LinearLayout(context)
            verticalLayout2.orientation = LinearLayout.VERTICAL
//            verticalLayout2.layoutParams = verticalParams
            horizontalLayout.addView(verticalLayout2)

            var menu = TextView(context)
            menu.setText(menuArray[index])
            menu.layoutParams = menuParams
            menu.setTextSize(Dimension.SP, 24.0f)
            menu.setPadding(0, 50, 0, 0)
            verticalLayout2.addView(menu)

            var cost = TextView(context)
            cost.setText("₩ ${costArray[index]}")
            cost.setTextSize(Dimension.SP, 20.0f)
            cost.setPadding(0, 50, 0, 0)
            verticalLayout2.addView(cost)

            var btn = view.findViewById<ImageButton>(imageButton.id)
            var clickMenuImage = imageArray[index]
            var clickMenuName = menuArray[index]
            var clickMenuCost = costArray[index]
            btn!!.setOnClickListener{
                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, ClickMenuFragment(clickMenuImage, clickMenuName, clickMenuCost)).commit()
            }
        }
    }


}
//        var menuLayout = view.findViewById<LinearLayout>(R.id.menuLayout)
//        var menu = resources.getStringArray(R.array.menu_name)
//        var cost = resources.getIntArray(R.array.menu_cost)
//        var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
//        var verticalParams = LinearLayout.LayoutParams(540,LinearLayout.LayoutParams.WRAP_CONTENT)
//        var imageParams = LinearLayout.LayoutParams(500,650)
//        var checkNum : Int = 0
//        var beverageNum : Int = 0
//        var costNum : Int = 0
//        imageParams.gravity = Gravity.CENTER
//        for (index in 0 until ceil(menu.count().toDouble()/2.0).toInt()){
//            for (num in 0 until index+1){
//                beverageNum = num
//            }
//            checkNum = menu.count()/2
//
//            for (Num in (menu.count()-checkNum) until menu.count()) {
//                costNum = Num
////                Log.d("ms","${menu.count()-checkNum}")
//            }
//
//            var horizontalLayout = LinearLayout(context)
//            horizontalLayout.orientation = LinearLayout.HORIZONTAL
//            horizontalLayout.layoutParams = params
//            horizontalLayout.weightSum = 2.0f
//            menuLayout.addView(horizontalLayout)
//
//            var verticalLayout1 = LinearLayout(context)
//            verticalLayout1.orientation = LinearLayout.VERTICAL
//            verticalLayout1.layoutParams = verticalParams
//            horizontalLayout.addView(verticalLayout1)
//
//            var verticalLayout2 = LinearLayout(context)
//            verticalLayout2.orientation = LinearLayout.VERTICAL
//            verticalLayout2.layoutParams = verticalParams
//            horizontalLayout.addView(verticalLayout2)
//
//            var imageButton1 = ImageButton(context)
//            imageButton1.layoutParams = imageParams
//            imageButton1.setBackgroundResource(R.drawable.coffee1)
//            verticalLayout1.addView(imageButton1)
//
//            var menuNameHorizontal1 = LinearLayout(context)
//            menuNameHorizontal1.orientation = LinearLayout.HORIZONTAL
//            menuNameHorizontal1.layoutParams = params
//            verticalLayout1.addView(menuNameHorizontal1)
//
//            var menu1 = TextView(context)
//            menu1.id = index
//            Log.d("ms","${beverageNum}")
//            menu1.setText(menu[beverageNum])
//            Log.d("message","${menu[beverageNum]}")
//            menu1.setTextSize(Dimension.SP, 20.0f)
//            menu1.setPadding(0, 50, 0, 0)
//            menuNameHorizontal1.addView(menu1)
//
//            var cost1 = TextView(context)
//            cost1.id = index
//            cost1.setText(" ₩ ${cost[beverageNum]}")
//            cost1.setTextSize(Dimension.SP, 20.0f)
//            cost1.setPadding(0, 50, 0, 0)
//            menuNameHorizontal1.addView(cost1)
//
////            var cost1 = TextView(context)
////            cost1.id = index
////            cost1.setText(" ₩ ${cost[costNum]}")
////            cost1.setTextSize(Dimension.SP, 20.0f)
////            cost1.setPadding(0, 50, 0, 0)
////            menuNameHorizontal1.addView(cost1)
//
//            var imageButton2 = ImageButton(context)
//            imageButton2.layoutParams = imageParams
//            imageButton2.setBackgroundResource(R.drawable.coffee2)
////            imageButton2.setPadding(0, 0, 0, 20)
//            verticalLayout2.addView(imageButton2)
//
//            var menuNameHorizontal2 = LinearLayout(context)
//            menuNameHorizontal2.orientation = LinearLayout.HORIZONTAL
//            menuNameHorizontal2.layoutParams = params
//            verticalLayout2.addView(menuNameHorizontal2)
//
//            var menu2 = TextView(context)
//            menu2.id = index
////            Log.d("message","${costNum}")
//            menu2.setText(menu[costNum])
//            menu2.setTextSize(Dimension.SP, 20.0f)
//            menu2.setPadding(0, 50, 0, 0)
//            menuNameHorizontal2.addView(menu2)
//
//            var cost2 = TextView(context)
//            cost2.setText("₩ 3000")
//            cost2.setTextSize(Dimension.SP, 20.0f)
//            cost2.setPadding(0, 50, 0, 0)
//            menuNameHorizontal2.addView(cost2)
