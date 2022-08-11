package com.jacobarau.smoothptz.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.jacobarau.smoothptz.MainViewModel
import com.jacobarau.smoothptz.R
import com.jacobarau.smoothptz.fragments.main.AllCamerasFragment
import com.jacobarau.smoothptz.fragments.main.NoCamerasAddedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment(R.layout.main_fragment) {
    private val model by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.cameras.observe(viewLifecycleOwner) { cameras ->
            childFragmentManager.commit {
                setReorderingAllowed(true)
                if (cameras.isEmpty()) {
                    replace<NoCamerasAddedFragment>(R.id.fragment_container_view)
                } else {
                    replace<AllCamerasFragment>(R.id.fragment_container_view)
                }
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.settings) {
                    findNavController().navigate(R.id.action_global_settingsFragment)
                    return true
                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}