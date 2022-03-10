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
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import kotlinx.android.synthetic.main.signup_fragment.*
import java.util.regex.Pattern


class SignupFragment : Fragment() {
    val dbControl = DatabaseControl()
    var idCheckNum = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.signup_fragment, container, false)
        var cancelBtn = view.findViewById<Button>(R.id.signup_cancel_btn)
        val idCheckBtn = view.findViewById<Button>(R.id.id_check_btn)

        val db = DataBase(context,"Account.db",null,1)  //데어터베이스 class 객체 선언
        val readableDb = db.readableDatabase//데이터베이스 객체를 읽기 가능 상태로 만듦
        val writeableDb = db.writableDatabase

        var numberList = arrayListOf<String>("0","1","2","3","4","5","6","7","8","9")
        var passWord = view.findViewById<EditText>(R.id.edit_up_pw)
        var confirmBtn = view.findViewById<Button>(R.id.confirm_btn)

        emailCheck(view)
        phoneCheck(view)
        pwMatchMismatch(view,passWord)
        pwCheck(passWord,numberList)
        signUpEvent(view,writeableDb,confirmBtn)

        cancelBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
        }

        idCheckBtn.setOnClickListener{
            idCheck(view,readableDb)
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
            idCheckNum += 1
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

    fun emailCheck(view: View) {
        var email = view.findViewById<EditText>(R.id.email_text)
        email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email_check.setText("옳지않은\n형식")
                    email_check.setTextColor((getResources().getColor(R.color.red)))
                } else{
                    email_check.setText("")
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun phoneCheck(view: View) {
        var phoneNum = view.findViewById<EditText>(R.id.phone_num_text)
        phoneNum.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", phoneNum.getText().toString())) {
                    phone_check.setText("옳지않은\n형식")
                    phone_check.setTextColor((getResources().getColor(R.color.red)))
                } else{
                    phone_check.setText("")
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun pwMatchMismatch(view: View,passWord:EditText) {
        var passWordConfirm = view.findViewById<EditText>(R.id.edit_up_pw2)
        passWordConfirm.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(passWord.getText().toString() == passWordConfirm.getText().toString()){
                   pwMatch()
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (passWord.getText().toString() != passWordConfirm.getText().toString()){
                    pwMisMatch()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        passWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(passWord.getText().toString() == passWordConfirm.getText().toString()){
                    pwMatch()
                }
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (passWord.getText().toString() != passWordConfirm.getText().toString()){
                    pwMisMatch()
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    fun pwMatch() {
        pw_confirm.setText("비밀번호\n 일치")
        pw_confirm.setTextColor((getResources().getColor(R.color.ediyacolor)))
    }

    fun pwMisMatch() {
        pw_confirm.setText("비밀번호\n 불일치")
        pw_confirm.setTextColor((getResources().getColor(R.color.red)))
    }

    fun pwCheck(passWord: EditText,numberList:ArrayList<String>){
        passWord.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(passWord.getText().toString().length < 4){
                    pw_check.setText("너무\n짧음")
                    pw_check.setTextColor((getResources().getColor(R.color.red)))
                }
                else if (passWord.getText().toString().length >= 4) {
                    for (index in 0 until passWord.getText().toString().length) {
                        if (passWord.getText()[index].toString() in numberList) {
                            pw_check.setText("사용\n가능")
                            pw_check.setTextColor((getResources().getColor(R.color.ediyacolor)))
                        } else{
                            pw_check.setText("숫자\n필요")
                            pw_check.setTextColor((getResources().getColor(R.color.red)))
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

    fun signUpEvent(view: View,writeableDb:SQLiteDatabase,confirmBtn: Button) {
        val firstName = view.findViewById<EditText>(R.id.first_name_text).getText()
        val lastName = view.findViewById<EditText>(R.id.last_name_text).getText()
        val realName = "${lastName}${firstName}"
        val userName = view.findViewById<EditText>(R.id.user_name_text).getText()
        val email = view.findViewById<EditText>(R.id.email_text).getText()
        val phoneNum = view.findViewById<EditText>(R.id.phone_num_text).getText()
        val passWord = view.findViewById<EditText>(R.id.edit_up_pw).getText()
        val passWordConfirm = view.findViewById<EditText>(R.id.edit_up_pw2).getText()

        var data = arrayListOf<String>(realName,userName.toString(),email.toString(),phoneNum.toString(),passWord.toString())
        var idPwData = arrayListOf<String>(userName.toString(),passWord.toString())

        var idPwColumn = arrayListOf<String>("id","pw")
        var column = arrayListOf<String>("name","id","email","phonenum","pw")

        var firstNameText = "이름을 입력해주세요"
        var LastNameText = "성을 입력해주세요"
        var idEmptyText = "아이디를 입력해주세요"
        var idCheckText = "아이디 중복확인을 해주세요"
        var emailText = "이메일을 입력해주세요"
        var emailCheckText = "이메일 형식을 확인해주세요"
        var phoneText = "전화번호를 입력해주세요"
        var phoneCheckText = "전화번호 형식을 확인해주세요"
        var pwText = "비밀번호를 입력해주세요"
        var pwConfirmText = "비밀번호를 확인해주세요"


            confirmBtn.setOnClickListener{
                if (firstName.count()==0 ){
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(firstNameText)
                } else if (lastName.count()==0){
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(LastNameText)
                } else if (userName.count() ==0){
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(idEmptyText)
                } else if (idCheckNum == 0) {
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(idCheckText)
                } else if (email.count() == 0) {
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(emailText)
                } else if (email_check.getText() == "옳지않은\n형식") {
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(emailCheckText)
                } else if (phoneNum.count() == 0) {
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(phoneText)
                } else if (phone_check.getText() == "옳지않은\n형식") {
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(phoneCheckText)
                } else if (passWord.count() == 0) {
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(pwText)
                } else if (pw_check.getText() == "숫자\n필요" || pw_check.getText() == "너무\n짧음" ||pw_confirm.getText() == "비밀번호\n 불일치"|| passWordConfirm.count() == 0) {
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(pwConfirmText)
                } else {
                    var signupComplete = "회원가입 완료! ${userName}님 환영합니다"
                    var mainActivity = activity as MainActivity
                    mainActivity.shortToastShow(signupComplete)
                    dbControl.create(writeableDb,"idpw_table",idPwColumn,idPwData)
                    dbControl.create(writeableDb,"user_table",column,data)
                    parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
                }
            }
        }
    }



