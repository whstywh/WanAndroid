package com.wh.wanandroid.ui.video

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.PowerManager
import android.view.SurfaceHolder
import com.wh.wanandroid.base.App

class MediaPlayerHelper(var context: Context) {

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }

    private var mPath: String? = null

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { MediaPlayerHelper(App.instance().applicationContext) }
    }

    fun setPath(path: String, listener: ((w: Int, h: Int) -> Unit)? = null) {
        if (mediaPlayer.isPlaying == true) {
            if (path == mPath) {
                listener?.let { it(mediaPlayer.videoWidth, mediaPlayer.videoHeight) }
                return
            }
            mediaPlayer.reset()
        }
        mPath = path

        mediaPlayer.apply {
            setWakeMode(context, PowerManager.PARTIAL_WAKE_LOCK)
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MOVIE).build()
            )
            setDataSource(context, Uri.parse(path))
            setOnVideoSizeChangedListener { _, w, h ->
                listener?.let { it(w, h) }
            }
            prepareAsync()
            setOnPreparedListener {
                start()
            }
        }
    }

    fun start() {
        if (mediaPlayer.isPlaying != true) {
            mediaPlayer.start()
        }
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun release() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    fun setDisplay(holder: SurfaceHolder) {
        mediaPlayer.setDisplay(holder)
    }

    fun getWidth(): Int {
        return mediaPlayer.videoWidth
    }

    fun getHeight(): Int {
        return mediaPlayer.videoHeight
    }
}

