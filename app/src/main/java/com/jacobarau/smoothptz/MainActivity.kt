package com.jacobarau.smoothptz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.jacobarau.smoothptz.settings.Camera
import com.jacobarau.smoothptz.settings.CameraRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    fun play(url: String, player: SimpleExoPlayer) {
        val mediaItem: MediaItem = MediaItem.fromUri(url)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel.cameras.observe(this, Observer { Log.i("foo", "new camera list gotten, $it") })

//        cameras = cameraRepository.cameras.asLiveData()
//        cameras.observe(this, Observer { Log.i("foo", "new camera list gotten, $it") })
//
//        lifecycleScope.launch {
//            cameraRepository.addCamera(Camera("foo", "bar"))
//        }
//        mainViewModel.addCamera(camera = Camera("foo", "bar"))

//        mainViewModel.deleteCamera(0)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tv)
        tv.text = HelloJNI().foo.toString()

        val player1 = SimpleExoPlayer.Builder(this).build()
        val playerView1: StyledPlayerView = findViewById(R.id.player1)
        playerView1.player = player1

        val player2 = SimpleExoPlayer.Builder(this).build()
        val playerView2: StyledPlayerView = findViewById(R.id.player2)
        playerView2.player = player2

        val prefs = getSharedPreferences("smoothptzprefs", MODE_PRIVATE)

        val player1urledit: EditText = findViewById(R.id.player1Button)
        val player1url = prefs.getString("url1", "")!!
        player1urledit.setText(player1url)
        play(player1url, player1)

        val player2urledit: EditText = findViewById(R.id.player2Button)
        val player2url = prefs.getString("url2", "")!!
        player2urledit.setText(player2url)
        play(player2url, player2)

        player1urledit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                prefs.edit {
                    this.putString("url1", s!!.toString())
                    this.commit()
                }
                play(s!!.toString(), player1)
                }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        player2urledit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                prefs.edit {
                    this.putString("url2", s!!.toString())
                    this.commit()
                }
                play(s!!.toString(), player2)
            }

            override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
            }

        })
    }
}