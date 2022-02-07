package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class PaymentFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.payment_fragment, container, false)
        var gobackBtn = view.findViewById<Button>(R.id.payment_goback_btn)
        var cardPayBtn = view.findViewById<ImageButton>(R.id.card_pay_btn)
        var barcodePayBtn = view.findViewById<ImageButton>(R.id.barcode_pay_btn)

        gobackBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }
        cardPayBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, CardPayFragment()).commit()
        }
        barcodePayBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, BarCodePayFragment()).commit()
        }

        return view

    }
}