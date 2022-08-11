package com.jacobarau.smoothptz

import androidx.lifecycle.*
import com.jacobarau.smoothptz.settings.Camera
import com.jacobarau.smoothptz.settings.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(private val cameraRepository: CameraRepository): ViewModel() {
    val cameras: LiveData<List<Camera>> by lazy {
        cameraRepository.cameras.asLiveData(viewModelScope.coroutineContext)
    }

    fun addCamera(camera: Camera) {
        viewModelScope.launch {
            cameraRepository.save(camera)
        }
    }
}