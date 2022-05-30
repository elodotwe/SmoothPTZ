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

    /**
     * Persists the given `Camera` in the `CameraRepository`.
     *
     * If the given Camera has been `save()`ed before (per its UUID), the existing Camera will be
     * updated with the provided Camera's details.
     *
     * @param camera: the camera to be saved or updated
     * @param before: the index at which to insert the given camera. If null and saving a new camera,
     *                it will be positioned at the end of the cameras list. If null and updating
     *                an existing camera, its position will remain the same.
     */
    suspend fun save(camera: Camera, before: Int? = null) {
        val cameraToAdd = com.jacobarau.smoothptz.settings.proto.Camera.newBuilder()
            .setName(camera.name)
            .setStreamURL(camera.streamURL)
            .setUuid(camera.uuid)
            .build()

        appContext.settingsDataStore.updateData { settings ->
            val settingsBuilder = settings.toBuilder()
            val indexOfExisting = settings.camerasList.indexOfFirst { it.uuid == camera.uuid }
            // Remove the camera if it exists in our collection; we'll re-add it per the `before` index.
            if (indexOfExisting >= 0) {
                settingsBuilder.removeCameras(indexOfExisting)
            }
            // If `before` is specified, use that index. If not, use the index of the existing camera.
            // (If there is no existing index, `newIndex` will be set to `null` and the new camera
            // will be appended to the list.)
            val newIndex = if (indexOfExisting < 0) { before } else { indexOfExisting }

            if (newIndex == null) {
                settingsBuilder
                    .addCameras(cameraToAdd)
                    .build()
            } else {
                settingsBuilder
                    .addCameras(newIndex, cameraToAdd)
                    .build()
            }
        }
    }

    /**
     * Removes the given camera from the cameras list.
     *
     * @param camera: the camera to be removed. If this camera is not in the cameras list, nothing
     *                happens.
     */
    suspend fun delete(camera: Camera) {
        appContext.settingsDataStore.updateData { settings ->
            val index = settings.camerasList.indexOfFirst { it.uuid == camera.uuid }
            if (index >= 0) {
                settings.toBuilder()
                    .removeCameras(index)
                    .build()
            } else {
                settings
            }
        }
    }
}