package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class LoginFragment: Fragment() {
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
        return view
    }
}