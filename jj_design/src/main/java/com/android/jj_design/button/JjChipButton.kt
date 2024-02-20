package com.android.jj_design.button

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.android.jj_design.R
import com.android.jj_design.utils.button.Confirm
import com.android.jj_design.utils.button.Delete
import com.android.jj_design.utils.button.Follow
import com.android.jj_design.utils.button.Following
import com.android.jj_design.utils.button.JjChipType
import com.android.jj_design.utils.button.Requested
import com.android.jj_design.utils.px
import com.android.jj_design.utils.toJjChipType

class JjChipButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : AppCompatButton(context, attributeSet, defStyleAttr) {
    init {
        initSetting()

        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.JjChipButton, 0, 0
        ).apply {
            this@JjChipButton.let { smallBtnAttr ->
                // item type
                setChipItemType(getInt(R.styleable.JjChipButton_jjChipType, 0).toJjChipType)
            }
            recycle()
        }
    }

    private fun initSetting() {
        setTextAppearance(R.style.JJ_TextAppearance_Medium_12)
        minWidth = MIN_WIDTH.px
        gravity = Gravity.CENTER
        setPadding(0, VERTICAL_PADDING.px, 0, VERTICAL_PADDING.px)
    }

    fun setChipItemType(type: JjChipType?) {
        when (type) {
            Confirm -> {
                text = type.name
                setTextColor(ContextCompat.getColor(context, R.color.color_white))
                setBackgroundResource(R.drawable.jj_small_button_rect_solid_f6bf54)
            }

            Delete -> {
                text = type.name
                setTextColor(ContextCompat.getColor(context, R.color.color_222222))
                setBackgroundResource(R.drawable.jj_small_button_rect_solid_eeeeee)
            }

            Follow -> {
                text = type.name
                setTextColor(ContextCompat.getColor(context, R.color.color_white))
                setBackgroundResource(R.drawable.jj_small_button_rect_solid_ff7f23)
            }

            Following -> {
                text = type.name
                setTextColor(ContextCompat.getColor(context, R.color.color_222222))
                setBackgroundResource(R.drawable.jj_small_button_rect_solid_eeeeee)
            }

            Requested -> {
                text = type.name
                setTextColor(ContextCompat.getColor(context, R.color.color_666666))
                setBackgroundResource(R.drawable.jj_small_button__rect_solid_ffffff_stroke_222222)
            }

            else -> Unit
        }
    }

    companion object {
        private const val MIN_WIDTH = 64
        private const val VERTICAL_PADDING = 8
    }
}
