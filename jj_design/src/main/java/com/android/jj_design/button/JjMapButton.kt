package com.android.jj_design.button

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import com.android.jj_design.R
import com.android.jj_design.utils.button.Add
import com.android.jj_design.utils.button.JjMapType
import com.android.jj_design.utils.button.Minus
import com.android.jj_design.utils.button.Position
import com.android.jj_design.utils.px
import com.android.jj_design.utils.toJjMapType

class JjMapButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attributeSet, defStyleAttr) {
    private val imageButtonPaint = Paint()
    private var imageBitmap: Bitmap? = null

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.JjMapButton, 0, 0
        ).apply {
            setJjMapType(getInt(R.styleable.JjMapButton_jjMapType, 0).toJjMapType)
            recycle()
        }
    }

    fun setJjMapType(type: JjMapType?) {
        when (type) {
            Add -> setJjMapUi(R.color.color_white, R.drawable.icon_add, R.color.color_666666)
            Minus -> setJjMapUi(R.color.color_white, R.drawable.icon_minus, R.color.color_666666)
            Position -> setJjMapUi(
                R.color.color_FF7F23,
                R.drawable.icon_position,
                R.color.color_FFFFFF
            )

            else -> Unit
        }
    }

    private fun setJjMapUi(
        @ColorRes buttonColor: Int,
        @DrawableRes imageDrawable: Int,
        @ColorRes imageColor: Int,
    ) {
        imageButtonPaint.apply {
            imageButtonPaint.color = context.getColor(buttonColor)
            setShadowLayer(
                IMAGE_SHADOW.px.toFloat() / 2,
                IMAGE_SHADOW.px.toFloat(),
                IMAGE_SHADOW.px.toFloat(),
                Color.LTGRAY
            )
        }
        imageBitmap = ContextCompat.getDrawable(context, imageDrawable).also { icon ->
            icon?.mutate()
        }?.also { iconDrawable ->
            DrawableCompat.setTint(iconDrawable, context.getColor(imageColor))
        }?.toBitmap(IMAGE_SIZE.px, IMAGE_SIZE.px)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (measureMode(widthMeasureSpec) && measureMode(heightMeasureSpec)) {
            setMeasuredDimension(
                MeasureSpec.getMode(widthMeasureSpec),
                MeasureSpec.getMode(heightMeasureSpec)
            )
        } else {
            setMeasuredDimension(LAYOUT_SIZE.px + IMAGE_SHADOW.px, LAYOUT_SIZE.px + IMAGE_SHADOW.px)
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
            imageButtonPaint
        )
        imageBitmap?.let {
            canvas?.drawBitmap(
                it,
                IMAGE_POSITION_X.px.toFloat(),
                IMAGE_POSITION_Y.px.toFloat(),
                null
            )
        }
    }

    companion object {
        private const val LAYOUT_SIZE = 64
        private const val IMAGE_SHADOW = 2

        private const val POSITION_X = LAYOUT_SIZE / 2
        private const val POSITION_Y = LAYOUT_SIZE / 2

        private const val RADIUS = LAYOUT_SIZE / 2

        private const val IMAGE_SIZE = 44
        private const val IMAGE_POSITION_X = (LAYOUT_SIZE - IMAGE_SIZE) / 2
        private const val IMAGE_POSITION_Y = (LAYOUT_SIZE - IMAGE_SIZE) / 2
    }
}
