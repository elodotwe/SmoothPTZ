package com.jacobarau.smoothptz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById(R.id.tv)
        tv.text = HelloJNI().foo.toString()

        val player = SimpleExoPlayer.Builder(this).build()
        val playerView: StyledPlayerView = findViewById(R.id.player)
        playerView.player = player

        val mediaItem: MediaItem = MediaItem.fromUri("rtsp://demo:demo@ipvmdemo.dyndns.org:5541/onvif-media/media.amp?profile=profile_1_h264&sessiontimeout=60&streamtype=unicast")
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }
}