package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class CardPayFragment: Fragment() {
    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?,savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.cardpay_fragment, container,false)
        var cancelBtn = view.findViewById<Button>(R.id.card_cancel_btn)
        var completeBtn = view.findViewById<Button>(R.id.card_complete_btn)

        cancelBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, PaymentFragment()).commit()
        }
        completeBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, CompleteFragment()).commit()
        }
        return view
    }
}