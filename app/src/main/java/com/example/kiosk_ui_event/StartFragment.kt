package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class StartFragment: Fragment() {
    var startNum = 0
    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?,savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.start_fragment, container,false)
        var togoBtn = view.findViewById<Button>(R.id.togoBtn)
        var instoreBtn = view.findViewById<Button>(R.id.instoreBtn)

        togoBtn!!.setOnClickListener{
            startNum+=1
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }
        instoreBtn!!.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, MenuListFragment()).commit()
        }
         return view
    }
}