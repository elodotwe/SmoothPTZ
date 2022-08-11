package com.jacobarau.smoothptz.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.jacobarau.smoothptz.R

class AddEditCameraFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_add_edit_camera, rootKey)
    }
}