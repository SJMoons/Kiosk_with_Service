package com.example.kiosk_ui_event

import android.os.Build
import android.os.Build.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.setting_fragment.*

class SettingFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.setting_fragment, container,false)
        var goBackBtn =  view.findViewById<Button>(R.id.set_back)

        var rbLight = view.findViewById<RadioButton>(R.id.rbLight)
        var rbDark = view.findViewById<RadioButton>(R.id.rbDark)
        var rbDefalut = view.findViewById<RadioButton>(R.id.rbDefault)

        rbLight!!.setOnClickListener{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        rbDark!!.setOnClickListener{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        rbDefalut!!.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
            }
        }

        goBackBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, LoginFragment()).commit()
        }

        return view
    }

}