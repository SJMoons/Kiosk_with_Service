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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.basket_fragment, container, false)
        var gobackBtn = view.findViewById<Button>(R.id.basket_goback_btn)
        var payBtn = view.findViewById<Button>(R.id.basket_pay_btn)

        var requestMenu = arguments?.getStringArrayList("menu")
        var requestCupCount = arguments?.getStringArrayList("cupCount")
        var requestTotalCost = arguments?.getStringArrayList("totalCost")

        var requestToppingQuantity = arguments?.getStringArrayList("toppingQuantity")
//        var toppingLocationNum = arguments?.getIntegerArrayList("toppingLocationNum")
        Log.d("messa","${requestToppingQuantity}")
        var requestappendToppingName = arguments?.getStringArrayList("toppingName")

//        Log.d("mess","${appendMenu.count()}")

        gobackBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }
        payBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea,PaymentFragment()).commit()
        }

        basketView(view,requestMenu!!,requestCupCount!!,requestTotalCost!!,requestToppingQuantity!!,requestappendToppingName!!)
        return view
    }


    fun basketView(view: View,requestMenu:ArrayList<String>,requestCupCount:ArrayList<String>,requestTotalCost:ArrayList<String>,requestToppingQuantity:ArrayList<String>,requestappendToppingName:ArrayList<String>) {

//        Log.d("mess","${requestMenu}")
//
//
//        Log.d("mess","${appendMenu.count()}")


        var basketLayout = view.findViewById<LinearLayout>(R.id.basket_linear)
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var menuParams = LinearLayout.LayoutParams(800,200)
        for (index in 0 until requestMenu.count()) {
            var itemText = TextView(context)
            itemText.id = index
            itemText.setText("${requestMenu[index]} ${requestCupCount[index]}잔 ${requestTotalCost[index]}원")
            itemText.layoutParams = menuParams
            itemText.setTextSize(Dimension.SP, 24.0f)
            basketLayout!!.addView(itemText)


//            var toppingText = TextView(context)
//            toppingText.id = index*10
//
//            toppingText.layoutParams = menuParams
//            itemText.setTextSize(Dimension.SP, 24.0f)
//
//            toppingText.setText()
//
//            basketLayout!!.addView(toppingText)

        }


    }
}