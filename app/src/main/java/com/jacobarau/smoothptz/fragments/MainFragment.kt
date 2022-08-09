package com.jacobarau.smoothptz.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.*
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
    }
}