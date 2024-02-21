package com.android.jj_design.button

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.android.jj_design.R
import com.android.jj_design.utils.px

class JjPlusButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attributeSet, defStyleAttr) {
    private val plusButtonPaint = Paint()
    private var plusBitmap: Bitmap?

    var iconDrawable: Int? = null
        set(@DrawableRes value) {
            plusBitmap = value?.let {
                ContextCompat.getDrawable(context, it)
                    ?.toBitmap(PLUS_IMAGE_SIZE.px, PLUS_IMAGE_SIZE.px)
            }
            invalidate()
            field = value
        }

    init {
        plusButtonPaint.apply {
            color = context.getColor(R.color.color_50EEEEEE)
        }
        plusBitmap = ContextCompat.getDrawable(context, R.drawable.icon_plus)
            ?.toBitmap(PLUS_IMAGE_SIZE.px, PLUS_IMAGE_SIZE.px)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measureMode(widthMeasureSpec) && measureMode(heightMeasureSpec)) {
            setMeasuredDimension(
                MeasureSpec.getMode(widthMeasureSpec),
                MeasureSpec.getMode(heightMeasureSpec)
            )
        } else {
            setMeasuredDimension(LAYOUT_SIZE.px, LAYOUT_SIZE.px)
        }
    }

    private fun measureMode(measureSpec: Int): Boolean =
        MeasureSpec.getMode(measureSpec) == MeasureSpec.EXACTLY

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(
            POSITION_X.px.toFloat(),
            POSITION_Y.px.toFloat(),
            RADIUS.px.toFloat(),
            plusButtonPaint
        )

        plusBitmap?.let {
            canvas?.drawBitmap(
                it,
                PLUS_IMAGE_POSITION_X.px.toFloat(),
                PLUS_IMAGE_POSITION_Y.px.toFloat(),
                null
            )
        }
    }

    companion object {
        private const val LAYOUT_SIZE = 24

        private const val POSITION_X = LAYOUT_SIZE / 2
        private const val POSITION_Y = LAYOUT_SIZE / 2

        private const val RADIUS = LAYOUT_SIZE / 2

        private const val PLUS_IMAGE_SIZE = 20
        private const val PLUS_IMAGE_POSITION_X = (LAYOUT_SIZE - PLUS_IMAGE_SIZE) / 2
        private const val PLUS_IMAGE_POSITION_Y = (LAYOUT_SIZE - PLUS_IMAGE_SIZE) / 2
    }
}