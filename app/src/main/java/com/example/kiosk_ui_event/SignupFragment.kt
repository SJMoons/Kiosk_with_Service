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
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.signup_fragment.*


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

        var numberList = arrayListOf<String>("0","1","2","3","4","5","6","7","8","9")
        var passWord = view.findViewById<EditText>(R.id.edit_up_pw)
        var confirmBtn = view.findViewById<Button>(R.id.confirm_btn)

        phoneCheck(view,numberList,confirmBtn)
        pwMatchMismatch(view,passWord,confirmBtn)
        pwCheck(passWord,confirmBtn,numberList)


        cancelBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
        }
        val idCheckBtn = view.findViewById<Button>(R.id.id_check_btn)
//        val confirmBtn = view.findViewById<Button>(R.id.confirm_btn)

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
            val passWord = view.findViewById<EditText>(R.id.edit_up_pw).text.toString()

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

    fun phoneCheck(view: View,numberList: ArrayList<String>,confirmBtn: Button) {
        var phoneNum = view.findViewById<EditText>(R.id.phone_num_text)
        phoneNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(phoneNum.getText().toString().length > 0){
                    for (index in 0 until phoneNum.getText().toString().length) {
                        if (phoneNum.getText()[index].toString() in numberList) {
                            phone_check.setText("")
                            confirmBtn.isEnabled=true
                        } else{
                            phone_check.setText("숫자만\n가능")
                            phone_check.setTextColor((getResources().getColor(R.color.red)))
                            confirmBtn.isEnabled=false
                        }
                    }
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun pwMatchMismatch(view: View,passWord:EditText,confirmBtn:Button) {
        var passWordConfirm = view.findViewById<EditText>(R.id.edit_up_pw2)
        passWordConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(passWord.getText().toString() == passWordConfirm.getText().toString()){
                   pwMatch(confirmBtn)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (passWord.getText().toString() != passWordConfirm.getText().toString()){
                    pwMisMatch(confirmBtn)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        passWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(passWord.getText().toString() == passWordConfirm.getText().toString()){
                    pwMatch(confirmBtn)
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (passWord.getText().toString() != passWordConfirm.getText().toString()){
                    pwMisMatch(confirmBtn)
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun pwMatch(confirmBtn: Button) {
        pw_confirm.setText("비밀번호\n 일치")
        pw_confirm.setTextColor((getResources().getColor(R.color.ediyacolor)))
        confirmBtn.isEnabled=true
    }

    fun pwMisMatch(confirmBtn: Button) {
        pw_confirm.setText("비밀번호\n 불일치")
        pw_confirm.setTextColor((getResources().getColor(R.color.red)))
        confirmBtn.isEnabled=false
    }

    fun pwCheck(passWord: EditText,confirmBtn:Button,numberList:ArrayList<String>) {
        passWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(passWord.getText().toString().length < 4){
                    pw_check.setText("너무\n짧음")
                    pw_check.setTextColor((getResources().getColor(R.color.red)))
                    confirmBtn.isEnabled=false
                }
                else if (passWord.getText().toString().length >= 4) {
                    for (index in 0 until passWord.getText().toString().length) {
                        if (passWord.getText()[index].toString() in numberList) {
                            pw_check.setText("사용\n가능")
                            pw_check.setTextColor((getResources().getColor(R.color.ediyacolor)))
                            confirmBtn.isEnabled=true
                        } else{
                            pw_check.setText("숫자\n필요")
                            pw_check.setTextColor((getResources().getColor(R.color.red)))
                            confirmBtn.isEnabled=false
                        }
                    }
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

}
