package com.example.imageselector.utils

import android.view.View
import androidx.databinding.BindingAdapter

object DataBindingAdapterUtil {
    @JvmStatic
    @BindingAdapter("select")
    fun select(view: View, b: Boolean) {
        view.isSelected = b
    }
}
