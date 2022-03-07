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
        var loginBtn = view.findViewById<Button>(R.id.login_btn)
        var signupBtn = view.findViewById<Button>(R.id.signup_btn)

        loginBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, StartFragment()).commit()
        }

        signupBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, SignupFragment()).commit()
        }

//        language_group.setOnClickListener{
//            when(language_group.checkedRadioButtonId) {
//                R.id.korean_btn
//
//            }
//        }


        return view
    }





}