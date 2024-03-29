package com.jacobarau.smoothptz.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.*
import androidx.preference.Preference.DEFAULT_ORDER
import com.jacobarau.smoothptz.MainViewModel
import com.jacobarau.smoothptz.R
import com.jacobarau.smoothptz.settings.Camera
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat() {
    private val model by viewModels<MainViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_root, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = preferenceManager.context
        // Per settings_root.xml, our first preference is the "Cameras" PreferenceCategory.
        // This contains the list of cameras, with an "add camera" button at the bottom.
        val camerasGroup = preferenceScreen.getPreference(0) as PreferenceCategory
        // Grab the "add" button off the bottom of this group. settings_root.xml only has the
        // "add" button in this group--but we may be recreated from a previous state,
        // meaning cameras may be added to this group from a previous run.
        val addButton = camerasGroup[camerasGroup.size - 1]
        model.cameras.observe(viewLifecycleOwner) { cameras ->
            // As it doesn't appear there's an "insert" method to manipulate the PreferenceCategory,
            // we remove all its children and re-add them on every update.
            camerasGroup.removeAll()
            cameras
                .map {
                    val pref = Preference(context)
                    pref.title = it.name
                    pref.summary = it.streamURL
                    pref.setIcon(R.drawable.ic_camera)
                    pref.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                        // TODO: nav to edit camera frag
                        Log.i("foo", "pref $pref clicked")
                        true
                    }
                    pref
                }
                .forEach {
                    camerasGroup.addPreference(it)
                }
            // Add button should be added to the end of the cameras list.
            // It will re-add at its previous position unless we reset the order property!
            addButton.order = DEFAULT_ORDER
            camerasGroup.addPreference(addButton)
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        if (preference.key == "add_camera") {
            findNavController().navigate(R.id.action_global_addEditCameraFragment)
            return true
        }
        return super.onPreferenceTreeClick(preference)
    }
}