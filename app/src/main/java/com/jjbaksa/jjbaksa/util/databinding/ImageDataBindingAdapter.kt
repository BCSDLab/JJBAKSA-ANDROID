package com.jjbaksa.jjbaksa.util.databinding

import android.widget.ImageView
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
