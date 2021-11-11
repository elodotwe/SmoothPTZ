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

    suspend fun addCamera(index: Int = -1, camera: Camera) {
        val cameraToAdd = com.jacobarau.smoothptz.settings.proto.Camera.newBuilder()
            .setName(camera.name)
            .setStreamURL(camera.streamURL)
            .build()

        appContext.settingsDataStore.updateData { settings ->
            if (index < 0) {
                settings.toBuilder()
                    .addCameras(cameraToAdd)
                    .build()
            } else {
                settings.toBuilder()
                    .addCameras(index, cameraToAdd)
                    .build()
            }
        }
    }

    suspend fun deleteCamera(index: Int) {
        appContext.settingsDataStore.updateData { settings ->
            settings.toBuilder()
                .removeCameras(index)
                .build()
        }
    }

    suspend fun modifyCameraAt(index: Int, camera: Camera) {
        appContext.settingsDataStore.updateData { settings ->
            val cameraToAdd = com.jacobarau.smoothptz.settings.proto.Camera.newBuilder()
                .setStreamURL(camera.streamURL)
                .setName(camera.name)
                .build()

            settings.toBuilder()
                .setCameras(index, cameraToAdd)
                .build()
        }
    }
}