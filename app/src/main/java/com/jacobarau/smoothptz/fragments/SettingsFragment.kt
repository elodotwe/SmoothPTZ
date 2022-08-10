package com.jacobarau.smoothptz.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.jacobarau.smoothptz.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.add_camera_preferences, rootKey)
    }
}