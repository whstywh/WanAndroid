package com.wh.wanandroid.ui.video

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.wh.wanandroid.base.App
import com.wh.wanandroid.databinding.LayoutFloatWindowBinding
import com.wh.wanandroid.utils.showToastKT
import java.lang.Exception

class FloatWindowManager(var context: Context) : View.OnClickListener {

    private val binding by lazy { LayoutFloatWindowBinding.inflate(LayoutInflater.from(context)) }

    private val windowManager: WindowManager by lazy { context.getSystemService(WINDOW_SERVICE) as WindowManager }

    private val windowParams: WindowManager.LayoutParams = WindowManager.LayoutParams().apply {
        x = context.resources.displayMetrics.widthPixels - 400 - 40
        y = context.resources.displayMetrics.heightPixels / 2
        width = 400
        height = 600
        type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        format = PixelFormat.TRANSLUCENT
    }

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { FloatWindowManager(App.instance().applicationContext) }
    }

    init {
        binding.root.apply {
            setOnClickListener(this@FloatWindowManager)
            setOnTouchListener(FloatingOnTouchListener())
        }
        binding.close.setOnClickListener(this)
    }

    fun showFloatingWindow() {
        try {
            if (Settings.canDrawOverlays(context)) {
                val surfaceView = SurfaceView(context).apply {
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
                    layoutParams = ConstraintLayout.LayoutParams(
                        MediaPlayerHelper.instance.getWidth(),
                        MediaPlayerHelper.instance.getHeight()
                    )
                }

                binding.surfaceLayout.addView(surfaceView)
                windowManager.addView(binding.root, windowParams)
            }
        } catch (e: Exception) {
            e.message.toString().showToastKT()
        }
    }

    fun closeFloatWindow() {
        if (binding.root.isAttachedToWindow) {
            binding.surfaceLayout.removeAllViews()
            windowManager.removeView(binding.root)
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
                    windowParams.x += moveX
                    windowParams.y += moveY
                    windowManager.updateViewLayout(binding.root, windowParams)
                }

                MotionEvent.ACTION_UP -> {
                    if (System.currentTimeMillis() - mActionDownTime <= 200) {
                        v?.performClick()
                    }
                }
            }
            return true
        }

    }

    override fun onClick(v: View?) {
        if (v == binding.root) {
            binding.surfaceBottomLayout.visibility =
                if (binding.surfaceBottomLayout.isVisible) View.INVISIBLE else View.VISIBLE

        } else if (v == binding.close) {
            MediaPlayerHelper.instance.release()
            closeFloatWindow()
        }
    }
}