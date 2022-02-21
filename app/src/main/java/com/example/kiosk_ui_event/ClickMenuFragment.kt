package com.example.kiosk_ui_event

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ClickMenuFragment(): Fragment() {
    lateinit var dataInterface: DataInterface
    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataInterface = context as DataInterface
    }
    var count: Int = 1
    var toppingCountString: String? = ""
    var toppingCountInt: Int = 0
    var appendToppingQuantity = arrayListOf<String>()  //
    var totalToppingPrice = mutableListOf<Int>()
    var toppingLocationNum = arrayListOf<Int>()   //
    var appendToppingName = arrayListOf<String>()    //
    var appendToppingNameNull = arrayListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.clickmenu_fragment, container, false)

        var menuName = arguments?.getString("menuName")
        var menuCost = arguments?.getString("menuCost")
        var menuImage = arguments?.getString("menuImage")

        var cancelBtn = view.findViewById<Button>(R.id.cancel_btn)
        var selectBtn = view.findViewById<Button>(R.id.select_btn)
        var addBtn = view.findViewById<Button>(R.id.add_btn)
        var minusBtn = view.findViewById<Button>(R.id.cups_minus_btn)
        var plusBtn = view.findViewById<Button>(R.id.cups_plus_btn)

        var menu = view.findViewById<TextView>(R.id.menu_name)
        menu.setText(menuName)
        var cost = view.findViewById<TextView>(R.id.cost)
        cost.setText("₩ ${menuCost.toString()}")
        var image = view.findViewById<ImageView>(R.id.image)
        image.setBackgroundResource(menuImage!!.toInt())

        minusBtn!!.setOnClickListener {
            count = count - 1
            if (count < 1) {
                count = 1
            }
            cups(menuCost!!)
        }
        plusBtn!!.setOnClickListener {
            count = count + 1
            cups(menuCost!!)
        }
        cancelBtn!!.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment())
                .commit()
        }
        selectBtn!!.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment())
                .commit()
            dataInterface.dataPass("${menuName}","${count}","${(menuCost!!.toInt()+totalToppingPrice.sum())*count}",appendToppingQuantity,toppingLocationNum,appendToppingName)
        }
        addBtn!!.setOnClickListener {
            topping(menuCost!!)
        }
        return view
    }

    fun topping(menuCost:String) {
        var popupView = getLayoutInflater().inflate(R.layout.topping_fragment, null)
        var alertdialog = AlertDialog.Builder(context).create()
        alertdialog.setView(popupView)
        alertdialog.show()
        alertdialog.window!!.setLayout(1100, 1700)

        var gobackBtn = popupView.findViewById<Button>(R.id.topping_goback_btn)
        gobackBtn!!.setOnClickListener {
            alertdialog.hide()
        }

        //토핑 개수 표시 알고리즘
        for (i in 0..8) {
            var toppingPlusBtn = popupView.findViewById<Button>(R.id.topping_plus_btn1 + i)
            var toppingCountView = popupView.findViewById<TextView>(R.id.topping_count_view1 + i)
            toppingPlusBtn.setOnClickListener {
                toppingCountString = toppingCountView.text.toString()
                toppingCountInt = toppingCountString!!.toInt()
                toppingCountInt = toppingCountInt + 1
                toppingCountView.setText(toppingCountInt.toString())
            }
            var toppingMinusBtn = popupView.findViewById<Button>(R.id.topping_minus_btn1 + i)
            toppingMinusBtn.setOnClickListener {
                toppingCountString = toppingCountView.text.toString()
                toppingCountInt = toppingCountString!!.toInt()
                toppingCountInt = toppingCountInt - 1
                if (toppingCountInt < 0) {
                    toppingCountInt = 0
                }
                toppingCountView.setText(toppingCountInt.toString())
            }
        //플러스한 토핑 개수들과 이름 리스트에 받아놓는 코드
            var completeBtn = popupView.findViewById<Button>(R.id.compelete_btn)
            var toppingName = resources.getStringArray(R.array.topping_name)
            completeBtn!!.setOnClickListener {
                for (locationIndex in 0..8) {
                    var toppingQuantity = popupView.findViewById<TextView>(R.id.topping_count_view1 + locationIndex).getText().toString()
                    appendToppingQuantity.add(toppingQuantity)
                    if (toppingQuantity.toInt()>0) {
                        toppingLocationNum.add(locationIndex)
                        appendToppingName.add(toppingName[locationIndex])
                    }
                }
                totalPrice(menuCost)
                alertdialog.hide()
            }
        }
    }

    fun cups(menuCost:String) {
        var cupCount = view?.findViewById<TextView>(R.id.cups_count)
        var cost = view?.findViewById<TextView>(R.id.cost)
        cupCount!!.setText(count.toString())
        cost!!.setText("₩ ${((menuCost!!.toInt()+totalToppingPrice.sum())*count)}")
    }

    fun totalPrice(menuCost: String) {
        var cost = view?.findViewById<TextView>(R.id.cost)
        var toppingCosts = resources.getIntArray(R.array.topping_cost)
        for (i in 0 until appendToppingQuantity.count()) {
            totalToppingPrice.add((appendToppingQuantity[i].toInt()*toppingCosts[i]))
        }
        cost!!.setText("₩ ${((menuCost!!.toInt()+totalToppingPrice.sum())*count)}")
    }
}