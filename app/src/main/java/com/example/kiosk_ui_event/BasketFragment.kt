package com.example.kiosk_ui_event

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class BasketFragment: Fragment() {
//    lateinit var dataInterface: DataInterface
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        dataInterface = context as DataInterface
//    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.basket_fragment, container, false)
        var goMenuBtn = view.findViewById<Button>(R.id.basket_goback_btn)
        var payBtn = view.findViewById<Button>(R.id.basket_pay_btn)
        var requestMenu = arguments?.getStringArrayList("menu")
        var requestCupCount = arguments?.getStringArrayList("cupCount")
        var requestTotalCost = arguments?.getStringArrayList("totalCost")
        var toppingList = arguments?.getStringArrayList("toppingList")
        var requestImage = arguments?.getStringArrayList("menuImage")

//        var num = 0
//        for (item in requestTotalCost!!) {
//            num += item.toInt()
//        }
//        var requestTotalCostInt = num

        if (requestMenu!!.count() == 0) {
                nothingBasket(view)
        } else {
            Log.d("size", "${requestMenu}")
            basketView(
                view,
                requestImage!!,
                requestMenu!!,
                requestCupCount!!,
                requestTotalCost!!,
                toppingList!!
                )
        }

        goMenuBtn!!.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment())
                .commit()
        }

        payBtn!!.setOnClickListener {
            if (requestMenu!!.count() != 0) {
                        var num = 0
                for (item in requestTotalCost!!) {
                    num += item.toInt()
                }
                var mainActivity = activity as MainActivity
                mainActivity.foreGroundServiceStart(requestMenu.count(),num)
                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, PaymentFragment())
                    .commit()
            }
        }
        return view
    }

    fun basketView(
        view: View,
        requestImage:ArrayList<String>,
        requestMenu: ArrayList<String>,
        requestCupCount: ArrayList<String>,
        requestTotalCost: ArrayList<String>,
        toppingList:ArrayList<String>
    ) {
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var basketLayout = view.findViewById<LinearLayout>(R.id.basket_linear)
        var menuParams = LinearLayout.LayoutParams(400, 200)
        var deleteParams = LinearLayout.LayoutParams(100,110)
//        var toppingParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,50)
        var totalPriceView = view.findViewById<TextView>(R.id.totalprice_view)
        var totalCountView = view.findViewById<TextView>(R.id.total_count)
        totalCountView.gravity = Gravity.CENTER
        totalCountView.setTextSize(Dimension.SP, 20.0f)
        totalCountView.setTextColor(Color.BLACK)
        totalCountView.setText("총 ${requestMenu.count()}개")

        for (index in 0 until requestMenu.count()) {
            var verticalLayout = LinearLayout(context)
            verticalLayout.id = (index+2)*7
            verticalLayout.orientation = LinearLayout.VERTICAL
            verticalLayout.layoutParams = params
            basketLayout!!.addView(verticalLayout)

            var horizontalLayout = LinearLayout(context)
            horizontalLayout.orientation = LinearLayout.HORIZONTAL
            horizontalLayout.layoutParams = params
            verticalLayout!!.addView(horizontalLayout)

            var itemText = TextView(context)
            itemText.id = (index+8)*4
            itemText.setText("${requestMenu[index]} ×${requestCupCount[index]} ${requestTotalCost[index]}원")
            itemText.layoutParams = menuParams
            itemText.setTextSize(Dimension.SP, 24.0f)
            horizontalLayout!!.addView(itemText)

            var deleteBtn = ImageButton(context)
            deleteBtn.id = (index+6)*11
            deleteBtn.setImageResource(R.drawable.deleteicon5)
            deleteBtn.layoutParams = deleteParams
            horizontalLayout!!.addView(deleteBtn)

            var toppingText = TextView(context)
            toppingText.id = index
            toppingText.setTextColor((getResources().getColor(R.color.black)))
            toppingText.layoutParams = params
            toppingText.setTextSize(Dimension.SP, 12.0f)
            toppingText.setText(toppingList[index])
            verticalLayout!!.addView(toppingText)
        }
        var num = 0
        for (item in requestTotalCost) {
            num += item.toInt()
        }
        totalPriceView.setText("₩ ${num}")
        deleteMenu(view,
            requestImage!!,
            requestMenu!!,
            requestCupCount!!,
            requestTotalCost!!,
            toppingList!!,
        )
    }

    fun deleteMenu(view:View, requestImage:ArrayList<String>,requestMenu: ArrayList<String>,requestCupCount:ArrayList<String>,requestTotalCost:ArrayList<String>,toppingList: ArrayList<String>) {
        for (index in 0 until requestMenu!!.count()) {
            var basketLayout = view.findViewById<LinearLayout>(R.id.basket_linear)
            var deleteBtn = view.findViewById<ImageButton>((index+6)*11)
            var verticalLayout = view.findViewById<LinearLayout>((index+2)*7)
            var totalPriceView =  view.findViewById<TextView>(R.id.totalprice_view)
            var totalCountView = view.findViewById<TextView>(R.id.total_count)
            totalCountView.gravity = Gravity.CENTER
            totalCountView.setTextSize(Dimension.SP, 20.0f)
            totalCountView.setTextColor(Color.BLACK)
            deleteBtn!!.setOnClickListener{
                basketLayout.removeView(verticalLayout)
                requestMenu.removeAt(index)
                requestCupCount.removeAt(index)
                toppingList.removeAt(index)
                requestTotalCost.removeAt(index)
                requestImage.removeAt(index)
                totalCountView.setText("총 ${requestMenu.count()}개")

                var num = 0
                for (item in requestTotalCost) {
                    num += item.toInt()
                }
                totalPriceView.setText("₩ ${num}")
                if (num==0) {
                    nothingBasket(view)
                }
            }
        }
    }

    fun nothingBasket(view: View) {
        var menuParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200)
        var basketLayout = view.findViewById<LinearLayout>(R.id.basket_linear)
        var totalPriceView =  view.findViewById<TextView>(R.id.totalprice_view)
        var text = resources.getString(R.string.korean_please_add_menu_to_cart)
        var itemText = TextView(context)
        itemText.setText(text)
        itemText.layoutParams = menuParams
        itemText.gravity = Gravity.CENTER
        itemText.setTextSize(Dimension.SP, 24.0f)
        itemText.setTextColor(Color.BLACK)
        basketLayout!!.addView(itemText)
        totalPriceView.setText("₩ 0")
    }
}