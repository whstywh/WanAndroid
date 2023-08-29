package com.wh.wanandroid.ui.video

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.SurfaceHolder
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.wh.wanandroid.databinding.ActivityVideoDetailBinding


class VideoDetailActivity : ComponentActivity() {

    private lateinit var binding: ActivityVideoDetailBinding

    private var mediaPlayer: MediaPlayer? = null

    private val uri: String =
        "https://vd2.bdstatic.com/mda-mk4igh3hr4syc22h/cae_h264_nowatermark/1636156879211786003/mda-mk4igh3hr4syc22h.mp4"

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            startService()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progress = intent.getIntExtra("progress", 0)

        mediaPlayer = MediaPlayer().apply {
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .build()
            )
            setDataSource(applicationContext, Uri.parse(uri))
            setOnPreparedListener {
                mediaPlayer?.seekTo(progress)
                start()
            }
            setOnVideoSizeChangedListener { _, width, height ->
                binding.surfaceView.layoutParams =
                    (binding.surfaceView.layoutParams as ConstraintLayout.LayoutParams).apply {
                        dimensionRatio = "h,${width}:${height}"
                    }
            }
            prepareAsync()
        }
        binding.surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                mediaPlayer?.setDisplay(holder)
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                mediaPlayer?.setDisplay(null)
            }
        })
        binding.surfaceView.setZOrderOnTop(false)
    }

    override fun onStart() {
        super.onStart()
        stopService()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    override fun onStop() {
        super.onStop()
        startService()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun startService() {
        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult.launch(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
            )
        } else {
            startService(Intent(this, FloatWindowService::class.java).apply {
                putExtra("uri", uri)
                putExtra("progress", mediaPlayer?.currentPosition ?: 0)
            })
        }
    }

    private fun stopService() {
        stopService(Intent(this, FloatWindowService::class.java))
    }
}