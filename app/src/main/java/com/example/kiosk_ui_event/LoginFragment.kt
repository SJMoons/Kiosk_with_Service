package com.example.kiosk_ui_event

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.login_fragment.*
import java.util.*

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

        loginBtn.setOnClickListener{
            val idValue = view.findViewById<EditText>(R.id.id_text).text.toString()
            val pwValue = view.findViewById<EditText>(R.id.pw_text).text.toString()
            val dbControl = DatabaseControl()

            var idPwData = arrayListOf<String>(idValue,pwValue)
            var idPwColumn = arrayListOf<String>("id","pw")
            val dataList = dbControl.read(readableDb,"idpw_table",idPwColumn,idPwData)
            if (dataList.size == 0) {
                Log.d("login result","login fault")
            }else {
                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, StartFragment()).commit()
            }
        }
        return view
    }
}