package com.example.kiosk_ui_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class CompleteFragment: Fragment() {
    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?,savedInstanceState: Bundle?): View {
        var view: View = inflater.inflate(R.layout.complete_fragment, container, false)

        return view
    }
}