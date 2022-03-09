package com.example.kiosk_ui_event

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class LoginFragment: Fragment() {
    lateinit var language_code:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.login_fragment, container, false)
        var signupBtn = view.findViewById<Button>(R.id.signup_btn)

        signupBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, SignupFragment()).commit()
        }

        val db = DataBase(context,"Account.db",null,1)  //데어터베이스 class 객체 선언
        val readableDb = db.readableDatabase            //데이터베이스 객체를 읽기 가능 상태로 만듦
        val loginBtn = view.findViewById<Button>(R.id.login_btn)
        var pwText = view.findViewById<EditText>(R.id.pw_text)

        pwText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.length >= 4) {
                    loginBtn.setClickable(true)
                    loginBtn.setTextColor((getResources().getColor(R.color.white)))
                    loginBtn.setBackgroundResource(R.drawable.blue_box)
                    loginBtn.setOnClickListener{
                        loginPass(view, readableDb)
                    }
                } else {
                    loginBtn.setClickable(false)
                    loginBtn.setTextColor((getResources().getColor(R.color.ediyacolor)))
                    loginBtn.setBackgroundResource(R.drawable.whitebox_line)
                }
            }
        })

        return view
    }

    fun loginPass(view:View,readableDb:SQLiteDatabase){
        val idValue = view.findViewById<EditText>(R.id.id_text).text.toString()
        val pwValue = view.findViewById<EditText>(R.id.pw_text).text.toString()
        val dbControl = DatabaseControl()
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        var idPwData = arrayListOf<String>(idValue,pwValue)
        var idPwColumn = arrayListOf<String>("id","pw")
        val dataList = dbControl.read(readableDb,"idpw_table",idPwColumn,idPwData)

        if (idValue.count() == 0 || dataList.size == 0) {
            builder.setMessage("이디야 ID 또는 비밀번호를 다시 확인해주세요")
            builder.setPositiveButton("확인",null)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        } else if (dataList.size != 0) {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, StartFragment()).commit()
        }
    }
}