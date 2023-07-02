package com.jjbaksa.jjbaksa.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardProvider() {
    fun hideKeyboard(context: Context, focusItem: View) {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(focusItem.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
