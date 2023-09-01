package com.wh.wanandroid.ui.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.wh.wanandroid.databinding.ActivityVideoDetailBinding

const val uri: String =
    "https://vd2.bdstatic.com/mda-mk4igh3hr4syc22h/cae_h264_nowatermark/1636156879211786003/mda-mk4igh3hr4syc22h.mp4"

class VideoDetailActivity : ComponentActivity() {

    private lateinit var binding: ActivityVideoDetailBinding

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            showFloatingWindow()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding.surfaceView.apply {
            setZOrderOnTop(false)
            holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    MediaPlayerHelper.instance.setDisplay(holder)
                }

                override fun surfaceChanged(
                    holder: SurfaceHolder, format: Int, width: Int, height: Int
                ) {
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                }
            })
        }
        MediaPlayerHelper.instance.setPath(uri) { w, h ->
            (binding.surfaceView.layoutParams as ConstraintLayout.LayoutParams).apply {
                dimensionRatio = "h,${w}:${h}"
            }
        }
    }


    override fun onResume() {
        super.onResume()
        closeFloatWindow()
    }

    override fun onPause() {
        showFloatingWindow()
        super.onPause()
    }

    private fun showFloatingWindow() {
        if (!Settings.canDrawOverlays(this)) {
            startActivityForResult.launch(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")
                )
            )
        } else {
            FloatWindowManager.instance.showFloatingWindow()
        }
    }

    private fun closeFloatWindow() {
        FloatWindowManager.instance.closeFloatWindow()
    }
}