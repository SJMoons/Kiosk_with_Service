package com.example.kiosk_ui_event

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ClickMenuFragment: Fragment() {
    var count: Int = 1
    var toppingCountString: String? = ""
    var toppingCountInt: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.clickmenu_fragment, container, false)
        var cancelBtn = view.findViewById<Button>(R.id.cancel_btn)
        var selectBtn = view.findViewById<Button>(R.id.select_btn)
        var addBtn = view.findViewById<Button>(R.id.add_btn)
        var minusBtn = view.findViewById<Button>(R.id.cups_minus_btn)
        var plusBtn = view.findViewById<Button>(R.id.cups_plus_btn)

        minusBtn!!.setOnClickListener {
            count = count - 1
            if (count < 1) {
                count = 1
            }
            cups()
        }
        plusBtn!!.setOnClickListener {
            count = count + 1
            cups()
        }
        cancelBtn!!.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment())
                .commit()
        }
        selectBtn!!.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment())
                .commit()
        }
        addBtn!!.setOnClickListener {
            topping()
        }
        return view
    }

    fun topping() {
        var popupView = getLayoutInflater().inflate(R.layout.topping_fragment, null)
        var alertdialog = AlertDialog.Builder(context).create()
        alertdialog.setView(popupView)
        alertdialog.show()
        alertdialog.window!!.setLayout(1100, 1700)

        var gobackBtn = popupView.findViewById<Button>(R.id.topping_goback_btn)
        gobackBtn!!.setOnClickListener {
            alertdialog.hide()
        }
        var completeBtn = popupView.findViewById<Button>(R.id.compelete_btn)
        completeBtn!!.setOnClickListener {
            alertdialog.hide()
        }
        for (i in 0..8) {
            var toppingPlusBtn = popupView.findViewById<Button>(R.id.topping_plus_btn1 + i)
            var toppingCountView = popupView.findViewById<TextView>(R.id.topping_count_view1 + i)
            toppingPlusBtn.setOnClickListener {
                toppingCountString = toppingCountView.text.toString()
                toppingCountInt = toppingCountString!!.toInt()
                toppingCountInt = toppingCountInt + 1
                toppingCountView.setText(toppingCountInt.toString())
            }
        }
        for (i in 0..8) {
            var toppingMinusBtn = popupView.findViewById<Button>(R.id.topping_minus_btn1 + i)
            var toppingCountView = popupView.findViewById<TextView>(R.id.topping_count_view1 + i)
            toppingMinusBtn.setOnClickListener {
                toppingCountString = toppingCountView.text.toString()
                toppingCountInt = toppingCountString!!.toInt()
                toppingCountInt = toppingCountInt - 1
                if (toppingCountInt < 0) {
                    toppingCountInt = 0
                }
                toppingCountView.setText(toppingCountInt.toString())
            }
        }

    }

    fun cups() {
        var cupCount = view?.findViewById<TextView>(R.id.cups_count)
        cupCount!!.setText(count.toString())
    }
}