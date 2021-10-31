package com.jacobarau.smoothptz.settings

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CameraRepository @Inject constructor(@ApplicationContext val appContext: Context) {
    val cameras = appContext.settingsDataStore.data.map { settings ->
        settings.camerasList.map { camera ->
            Camera(camera.name, camera.streamURL)
        }
    }

    suspend fun addCamera(camera: Camera) {
        val cameraToAdd = com.jacobarau.smoothptz.settings.proto.Camera.newBuilder()
            .setName(camera.name)
            .setStreamURL(camera.streamURL)
            .build()

        appContext.settingsDataStore.updateData { settings ->
            settings.toBuilder()
                .addCameras(cameraToAdd)
                .build()
        }
    }
}