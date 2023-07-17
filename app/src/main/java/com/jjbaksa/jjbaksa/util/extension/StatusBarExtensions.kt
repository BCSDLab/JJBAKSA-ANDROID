package com.jjbaksa.jjbaksa.util

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams

fun Activity.setExtendView(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { v, windowInsets ->
        val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = insets.top
        }
        WindowInsetsCompat.CONSUMED
    }
}
fun Activity.setStatusBarTransparent() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}
