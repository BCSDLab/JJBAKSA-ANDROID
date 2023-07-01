package com.jjbaksa.jjbaksa.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import com.jjbaksa.jjbaksa.R

class WindowProvider(
    private val context: Activity
) {
    fun clearStatusBar(color: Int){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            context.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            context.window.statusBarColor = color // settings transparent
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            context.window.addFlags(flags)
        }
        context.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}