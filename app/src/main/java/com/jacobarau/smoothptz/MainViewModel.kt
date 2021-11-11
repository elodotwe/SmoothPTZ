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

    fun addCamera(index: Int = -1, camera: Camera) {
        viewModelScope.launch {
            cameraRepository.addCamera(index, camera)
        }
    }

    fun deleteCamera(index: Int) {
        viewModelScope.launch {
            cameraRepository.deleteCamera(index)
        }
    }

    fun modifyCameraAt(index: Int, camera: Camera) {
        viewModelScope.launch {
            cameraRepository.modifyCameraAt(index = index, camera = camera)
        }
    }
}