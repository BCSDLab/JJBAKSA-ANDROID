package com.jjbaksa.jjbaksa.util.databinding

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.jjbaksa.jjbaksa.R

@BindingAdapter("img")
fun ImageView.setImage(image: String?) {
    if (image == null) {
        return
    }

    if (image.isEmpty()) {
        load(R.drawable.baseline_supervised_user_circle_24)
    } else {
        load(image) {
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("iconTint")
fun AppCompatTextView.setDrawableTint(enabled: Boolean) {
    if (enabled) {
        this.compoundDrawableTintList =
            ContextCompat.getColorStateList(context, R.color.color_ff7f23)
    } else {
        this.compoundDrawableTintList =
            ContextCompat.getColorStateList(context, R.color.color_666666)
    }
}
