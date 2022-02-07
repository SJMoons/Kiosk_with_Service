package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment


class MenuListFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.menulist_fragment, container,false)
        var gobackBtn = view.findViewById<Button>(R.id.menu_goback_btn)
        var basketBtn = view.findViewById<Button>(R.id.basket_btn)
        var payBtn = view.findViewById<Button>(R.id.basket_pay_btn)

        gobackBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, StartFragment()).commit()
        }

        basketBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, BasketFragment()).commit()
        }

        payBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, PaymentFragment()).commit()
        }

        return view
    }
}