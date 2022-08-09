package com.jacobarau.smoothptz.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.jacobarau.smoothptz.R

class NoCamerasAddedFragment: Fragment(R.layout.no_cameras_added_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener {
            Log.i("foo", "clicked")
        }
    }
}