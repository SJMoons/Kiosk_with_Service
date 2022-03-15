package com.example.kiosk_ui_event

import android.app.ActionBar
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Dimension
import androidx.core.view.marginRight
import androidx.fragment.app.Fragment

class StartFragment: Fragment() {
    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?,savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.start_fragment, container,false)
        var togoBtn = view.findViewById<Button>(R.id.togoBtn)
        var instoreBtn = view.findViewById<Button>(R.id.instoreBtn)
        var requestId = arguments?.getString("id")
        val db = DataBase(context,"Account.db",null,1)  //데어터베이스 class 객체 선언
        val readableDb = db.readableDatabase

        idMark(view,requestId!!)
        historyView(view,readableDb,requestId)

        togoBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }
        instoreBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }
         return view
    }

    fun idMark(view:View,requestId:String) {
        var userId = view.findViewById<TextView>(R.id.user_id_text)
        userId.setText("${requestId} ")
        userId.setTextSize(Dimension.SP, 17.0f)
        var secondUserId = view.findViewById<TextView>(R.id.user_id_text2)
        secondUserId.setText("${requestId}")
        secondUserId.setTextSize(Dimension.SP, 24.0f)
    }

    fun historyView(view: View,readableDb:SQLiteDatabase,requestId: String) {
        val dbControl = DatabaseControl()
        var idData = arrayListOf<String>(requestId)
        var Column = arrayListOf<String>("id")
        var sql = dbControl.makeReadSql("idhistory_table",Column,idData)
        val dataList = dbControl.readPaymentHistory(readableDb,sql)

        var horizontalLinear = view.findViewById<LinearLayout>(R.id.history_linear)
        var params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var imageParams = LinearLayout.LayoutParams(200, 200)
        var menuParams =  LinearLayout.LayoutParams(200, 60)
        for (index in 0 until dataList.count()) {
            var verticalLayout = LinearLayout(context)
            verticalLayout.id = (index+2)*8
            verticalLayout.orientation = LinearLayout.VERTICAL
            verticalLayout.layoutParams = params
            horizontalLinear.addView(verticalLayout)

            var imageView = ImageView(context)
            imageView.background = getResources().getDrawable(R.drawable.history_imageview, null)
            imageView.setImageResource(dataList[index][0].toInt())
            imageView.setClipToOutline(true)
            imageView.layoutParams = imageParams
            imageParams.setMargins(40,0,40,0)
            verticalLayout.addView(imageView)

            var menuView = TextView(context)
            menuView.setText(dataList[index][1])
            menuView.layoutParams = menuParams
            menuParams.gravity = Gravity.CENTER
            menuParams.setMargins(58,0,58,0)
            menuView.setTextSize(Dimension.SP, 13.0f)
            menuView.setTextColor(getResources().getColor(R.color.black))
            verticalLayout.addView(menuView)
        }


        Log.d("dataList","${dataList.size}")
    }

}