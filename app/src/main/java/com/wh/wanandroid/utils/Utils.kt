package com.wh.wanandroid.utils

import android.widget.Toast
import com.wh.wanandroid.base.App

fun String.showToastKT() {
    Toast.makeText(App.instance(), this, Toast.LENGTH_SHORT).show()
}
