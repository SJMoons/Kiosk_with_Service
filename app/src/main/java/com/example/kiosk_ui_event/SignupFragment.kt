package com.example.kiosk_ui_event

import android.accessibilityservice.GestureDescription
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast

import android.content.DialogInterface




class SignupFragment : Fragment() {
    val dbControl = DatabaseControl()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.signup_fragment, container, false)
        var cancelBtn = view.findViewById<Button>(R.id.signup_cancel_btn)

        val db = DataBase(context,"Account.db",null,1)  //데어터베이스 class 객체 선언
        val readableDb = db.readableDatabase//데이터베이스 객체를 읽기 가능 상태로 만듦
        val writeableDb = db.writableDatabase

        cancelBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
        }
        val idCheckBtn = view.findViewById<Button>(R.id.id_check_btn)
        val confirmBtn = view.findViewById<Button>(R.id.confirm_btn)

        idCheckBtn.setOnClickListener{
            idCheck(view,readableDb)
        }

        confirmBtn.setOnClickListener{
            val firstName = view.findViewById<EditText>(R.id.first_name_text).text.toString()
            val lastName = view.findViewById<EditText>(R.id.last_name_text).text.toString()
            val realName = lastName+firstName
            val userName = view.findViewById<EditText>(R.id.user_name_text).text.toString()
            val email = view.findViewById<EditText>(R.id.email_text).text.toString()
            val phoneNum = view.findViewById<EditText>(R.id.phone_num_text).text.toString()
            val passWord = view.findViewById<EditText>(R.id.password_text).text.toString()

            var data = arrayListOf<String>(realName,userName,email,phoneNum,passWord)
            var idPwData = arrayListOf<String>(userName,passWord)

            var idPwColumn = arrayListOf<String>("id","pw")
            var column = arrayListOf<String>("name","id","email","phonenum","pw")

            dbControl.create(writeableDb,"idpw_table",idPwColumn,idPwData)
            dbControl.create(writeableDb,"user_table",column,data)

            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
        }
        return view
    }

    fun idCheck(view:View,readableDb:SQLiteDatabase) {
        val getUserName = view.findViewById<EditText>(R.id.user_name_text).text.toString()
        val userNameEditText = view.findViewById<EditText>(R.id.user_name_text)
        val userNameData = arrayListOf<String>(getUserName)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        var idColumn = arrayListOf<String>("id")
        var dataList = dbControl.read(readableDb,"idpw_table",idColumn,userNameData)

        if (dataList.size == 0 && getUserName.count() != 0) {
            userNameEditText.setFocusable(false)
            userNameEditText.setClickable(false)
            builder.setMessage("사용하실 수 있는 아이디입니다")
            builder.setPositiveButton("확인",null)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        } else if (getUserName.count() == 0) {
            builder.setMessage("아이디를 입력해주세요")
            builder.setPositiveButton("확인",null)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        } else if (dataList.size != 0) {
            builder.setMessage("이미있는 아이디입니다")
            builder.setPositiveButton("확인",null)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }
}
