package com.example.kiosk_ui_event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class PaymentFragment: Fragment() {
    lateinit var dataInterface: DataInterface
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataInterface = context as DataInterface
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.payment_fragment, container, false)
        var goBasketBtn = view.findViewById<Button>(R.id.payment_goback_btn)
        var cardPayBtn = view.findViewById<ImageButton>(R.id.card_pay_btn)
        var barcodePayBtn = view.findViewById<ImageButton>(R.id.barcode_pay_btn)

        goBasketBtn!!.setOnClickListener{
            dataInterface.payToBasket()
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