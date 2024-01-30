package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import coil.load
import com.bumptech.glide.Glide
import com.jjbaksa.jjbaksa.GlideApp
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ItemMarkerBinding
import timber.log.Timber

class JjMarker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: ItemMarkerBinding
    private var markerCount: String? = null
    private var imageUrl: String? = null

    init {
        attrs?.let {
            initAttr(it)
        }
        initView()
    }

    private fun initAttr(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.JjMarker,
            0, 0
        ).apply {
            markerCount = getString(R.styleable.JjMarker_markerCount)
            imageUrl = getString(R.styleable.JjMarker_imageUrl)
        }
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_marker,
            this,
            true
        )

        markerCount?.let {
            setMarkerCount(it)
        }
        imageUrl?.let {
            setImageUrl(it)
        }
    }

    fun setMarkerCount(count: String) {
        this.markerCount = count
        binding.markerCountTextView.text = count
    }

    fun setImageUrl(imageUrl: String) {
        GlideApp.with(context)
            .load(imageUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_empty_img)
            .into(binding.shopImageView)
    }
}
