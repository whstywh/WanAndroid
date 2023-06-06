package com.wh.wanandroid.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.wh.wanandroid.base.App
import com.wh.wanandroid.base.BaseActivity

fun String.showToastKT() {
    Toast.makeText(App.instance(), this, Toast.LENGTH_SHORT).show()
}

inline fun <reified T : BaseActivity> Context.startActivityKT(block: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(block))
}

