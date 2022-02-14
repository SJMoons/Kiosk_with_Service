package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment

class AdeMenuFragment: Fragment() {
    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?,savedInstanceState: Bundle?): View {
        var view : View = inflater.inflate(R.layout.ademenu_fragment, container,false)
        for (i in 0..5) {
            var imageBtn = view.findViewById<ImageButton>(R.id.imageBtn1+i)
            imageBtn!!.setOnClickListener{
                parentFragmentManager.beginTransaction().replace(R.id.fragmentArea, ClickMenuFragment()).commit()
            }
        }
        return view
    }
}