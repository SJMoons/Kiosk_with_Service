package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class SignupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view: View = inflater.inflate(R.layout.signup_fragment, container, false)
        var confirmBtn = view.findViewById<Button>(R.id.confirm_btn)
        var cancelBtn = view.findViewById<Button>(R.id.signup_cancel_btn)

        confirmBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
        }
        cancelBtn.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
        }
        return view
    }
}