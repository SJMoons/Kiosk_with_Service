package com.example.kiosk_ui_event

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.fragment.app.Fragment

class BasketFragment: Fragment() {
    lateinit var dataInterface: DataInterface
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataInterface = context as DataInterface
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.basket_fragment, container, false)
        var gobackBtn = view.findViewById<Button>(R.id.basket_goback_btn)
        var payBtn = view.findViewById<Button>(R.id.basket_pay_btn)

        var requestMenu = arguments?.getStringArrayList("menu")
        var requestCupCount = arguments?.getStringArrayList("cupCount")
        var requestTotalCost = arguments?.getIntegerArrayList("totalCost")



//        var requestToppingQuantity = arguments?.getStringArrayList("toppingQuantity")
//        var requestSubList = arguments?.getStringArrayList("toppingSubList")
//        var requestOriginalToppingQuantity = arguments?.getStringArrayList("originalToppingQuantity")
//        var requestappendToppingName = arguments?.getStringArrayList("toppingName")
//
//        var toppingLocationNum = arguments?.getIntegerArrayList("toppingLocationNum")

        var list = arguments?.getStringArrayList("list")


        gobackBtn!!.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment())
                .commit()
        }
        payBtn!!.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, PaymentFragment())
                .commit()
        }

        basketView(
            view,
            requestMenu!!,
            requestCupCount!!,
            requestTotalCost!!,
//            requestappendToppingName!!,
            list!!
        )

        return view
    }


    fun basketView(
        view: View,
        requestMenu: ArrayList<String>,
        requestCupCount: ArrayList<String>,
        requestTotalCost: ArrayList<Int>,
//        requestappendToppingName: ArrayList<String>,
        list:ArrayList<String>
    ) {
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var basketLayout = view.findViewById<LinearLayout>(R.id.basket_linear)
        var menuParams = LinearLayout.LayoutParams(400, 200)
        var deleteParams = LinearLayout.LayoutParams(100,100)
        var toppingParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100)
        var totalPriceView = view.findViewById<TextView>(R.id.totalprice_view)
//        var basketMenu: ArrayList<ArrayList<String>> = ArrayList()
//
//        basketMenu.add(requestMenu)
//        basketMenu.add(requestappendToppingName)
//        Log.d("list","${basketMenu}")

        for (index in 0 until requestMenu.count()) {
            var horizontalLayout = LinearLayout(context)
            horizontalLayout.orientation = LinearLayout.HORIZONTAL
            horizontalLayout.layoutParams = params
            basketLayout!!.addView(horizontalLayout)

            var itemText = TextView(context)
            itemText.id = index
            itemText.setText("${requestMenu[index]} ×${requestCupCount[index]} ${requestTotalCost[index]}원")
            itemText.layoutParams = menuParams
            itemText.setTextSize(Dimension.SP, 24.0f)
            horizontalLayout!!.addView(itemText)

            var deleteBtn = Button(context)
            deleteBtn.id = index
            deleteBtn.setText("X")
            deleteBtn.layoutParams = deleteParams
            horizontalLayout!!.addView(deleteBtn)

            var toppingText = TextView(context)
            toppingText.id = index*7
            toppingText.layoutParams = toppingParams
            toppingText.setTextSize(Dimension.SP, 12.0f)
            toppingText.setText(list[index])
            basketLayout!!.addView(toppingText)

        }
        totalPriceView.setText("₩ ${requestTotalCost.sum()}")
    }

    fun nothingBasket(view: View) {
        var menuParams = LinearLayout.LayoutParams(400, 200)
        var basketLayout = view.findViewById<LinearLayout>(R.id.basket_linear)
        var itemText = TextView(context)
        itemText.setText("없음")
        itemText.layoutParams = menuParams
        itemText.setTextSize(Dimension.SP, 24.0f)
        basketLayout!!.addView(itemText)
    }

}