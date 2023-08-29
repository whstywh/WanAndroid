package com.wh.wanandroid.ui.video

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.provider.Settings
import android.view.*
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.wh.wanandroid.R


class FloatWindowService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    private val windowManager: WindowManager by lazy { getSystemService(WINDOW_SERVICE) as WindowManager }

    private val constraintLayout: ConstraintLayout by lazy {
        ConstraintLayout(this).apply {
            setBackgroundColor(Color.BLACK)
            setOnTouchListener(FloatingOnTouchListener())
        }
    }

    private val windowParams = WindowManager.LayoutParams().apply {
        width = 360
        height = 640
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }
        flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        format = PixelFormat.TRANSLUCENT
    }

    private val surfaceView by lazy {
        SurfaceView(this).apply {
            setZOrderOnTop(false)

            holder.addCallback(object : SurfaceHolder.Callback {
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
                }
            })
        }
    }

    private val closeView by lazy {
        ImageView(this).apply {
            visibility = View.GONE
            setImageDrawable(
                ContextCompat.getDrawable(
                    this@FloatWindowService,
                    R.drawable.login_error
                )
            )
            setOnClickListener {
                startActivity(
                    Intent(
                        this@FloatWindowService,
                        VideoDetailActivity::class.java
                    ).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra("progress", mediaPlayer?.currentPosition ?: 0)
                    })
                stopSelf()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra("uri")?.let {
            val position = intent.getIntExtra("progress", 0)
            initMediaPlayer(it, position)
            showFloatingWindow()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        windowManager.removeView(constraintLayout)
        super.onDestroy()
    }

    private fun initMediaPlayer(uri: String, position: Int?) {
        mediaPlayer = MediaPlayer().apply {
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                    .build()
            )
            setDataSource(applicationContext, Uri.parse(uri))
            setOnPreparedListener {
                mediaPlayer?.seekTo(position ?: 0)
                start()
            }
            setOnVideoSizeChangedListener { _, width, height ->
                surfaceView.layoutParams =
                    (surfaceView.layoutParams as ConstraintLayout.LayoutParams).apply {
                        dimensionRatio = "h,${width}:${height}"
                    }
            }
            prepareAsync()
        }
    }

    private fun showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {

            val closeParams = ConstraintLayout.LayoutParams(50, 50).apply {
                topMargin = 30
                marginEnd = 30
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
            constraintLayout.addView(closeView, closeParams)

            val layoutParams = ConstraintLayout.LayoutParams(0, 0).apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            }
            constraintLayout.addView(surfaceView, layoutParams)

            windowManager.addView(constraintLayout, windowParams.apply {
                gravity = (Gravity.END or Gravity.BOTTOM)
                x = 50
                y = 50
            })
        }
    }

    inner class FloatingOnTouchListener : View.OnTouchListener {

        private var x: Int = 0
        private var y: Int = 0
        private var mActionDownTime: Long = 0

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                    mActionDownTime = System.currentTimeMillis()
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val moveX = nowX - x
                    val moveY = nowY - y
                    x = nowX
                    y = nowY
                    windowParams.x -= moveX
                    windowParams.y -= moveY
                    windowManager.updateViewLayout(constraintLayout, windowParams)
                }
                MotionEvent.ACTION_UP -> {
                    if (System.currentTimeMillis() - mActionDownTime <= 200) {
                        v?.performClick()
                        closeView.visibility = if (closeView.isVisible) View.GONE else View.VISIBLE
                    }
                }
            }
            return false
        }
    }


}