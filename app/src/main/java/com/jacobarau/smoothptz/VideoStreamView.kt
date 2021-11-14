package com.jacobarau.smoothptz

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.widget.FrameLayout
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

class VideoStreamView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private val vlcLayout = VLCVideoLayout(context)
    private val libVLC = LibVLC(context, mutableListOf("-vvv"))
    private val mediaPlayer = MediaPlayer(libVLC)

    init {
        this.addView(vlcLayout)
        mediaPlayer.attachViews(vlcLayout, null, false, false)
    }

    fun play(url: String) {
        val media = Media(libVLC, Uri.parse(url))
        mediaPlayer.media = media
        media.release()
        mediaPlayer.play()
    }

    fun stop() {
        mediaPlayer.stop()
    }
}