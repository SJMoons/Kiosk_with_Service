package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.fragment.app.Fragment

class BasketFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.basket_fragment, container, false)
        var gobackBtn = view.findViewById<Button>(R.id.basket_goback_btn)
        var payBtn = view.findViewById<Button>(R.id.basket_pay_btn)

        var requestMenu = arguments?.getString("menu")
        var requestCupCount = arguments?.getString("cupCount")
        var requestTotalCost = arguments?.getString("totalCost")
//        var basketView = view.findViewById<TextView>(R.id.basket_view)
//        basketView.setText("${requestMenu!!} ${requestCupCount!!} ${requestTotalCost!!}")
        gobackBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }
        payBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea,PaymentFragment()).commit()
        }
        basketView(view,requestMenu!!,requestCupCount!!,requestTotalCost!!)

        return view
    }

    fun basketView(view: View, requestMenu:String,requestCupCount:String,requestTotalCost:String) {
        var basketLayout = view.findViewById<LinearLayout>(R.id.basket_linear)
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var menuParams = LinearLayout.LayoutParams(800,200)
        var itemText = TextView(context)
        itemText.setText("${requestMenu!!} ${requestCupCount!!}잔 ${requestTotalCost!!}원")
        itemText.layoutParams = menuParams
        itemText.setTextSize(Dimension.SP, 24.0f)
        basketLayout!!.addView(itemText)
    }
}