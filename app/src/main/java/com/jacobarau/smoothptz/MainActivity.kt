package com.jacobarau.smoothptz

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jacobarau.smoothptz.databinding.ActivityMainBinding
import com.jacobarau.smoothptz.databinding.ActivityMainSingleCameraViewBinding
import com.jacobarau.smoothptz.settings.Camera
import dagger.hilt.android.AndroidEntryPoint
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

//        mainViewModel.addCamera(camera = Camera("camera", "rtsp://demo:demo@ipvmdemo.dyndns.org:5541/onvif-media/media.amp?profile=profile_1_h264&sessiontimeout=60&streamtype=unicast"))
//        mainViewModel.addCamera(camera = Camera("blender test", "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4"))

        setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.cameras.observe(this, Observer { Log.i("foo", "new camera list gotten, $it") })

        mainViewModel.cameras
            .observe(this, {
                binding.noCameraAdded.root.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                binding.camerasLinearLayout.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            })

        mainViewModel.cameras
            .observe(this, { cameras ->
                binding.camerasLinearLayout.removeAllViews()
                cameras.forEach { camera ->
                    binding.camerasLinearLayout.addView(makeCameraView(camera))
                }
            })
    }

    private fun makeCameraView(camera: Camera): View {
        val cameraViewBinding = ActivityMainSingleCameraViewBinding.inflate(layoutInflater)
  
        cameraViewBinding.root.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)

        val libVLC = LibVLC(this, mutableListOf("-vvv"))
        val mediaPlayer = MediaPlayer(libVLC)
        mediaPlayer.attachViews(cameraViewBinding.videoLayout, null, false, false)
        val media = Media(libVLC, Uri.parse(camera.streamURL))

        mediaPlayer.media = media
        media.release()
        mediaPlayer.play()
        return cameraViewBinding.root
    }
}