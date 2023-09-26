
package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.ViewJEditTextBinding
import com.jjbaksa.jjbaksa.util.controlSoftKeyboard
import com.jjbaksa.jjbaksa.util.fromDpToPx
class JEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributeSet, defStyleAttr, defStyleRes) {
    private lateinit var binding: ViewJEditTextBinding

    var hintText = ""
        set(value) {
            binding.etJ.hint = value
            field = value
        }

    var text = ""
        set(value) {
            binding.etJ.setText(value)
            field = value
        }
    init {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.JEditText,
            defStyleAttr,
            defStyleRes
        ).apply {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_j_edit_text, this@JEditText, true)
            binding.clJ.minimumHeight = getDimension(R.styleable.JEditText_android_minHeight, 33.fromDpToPx()).toInt()
            binding.etJ.inputType = getInt(R.styleable.JEditText_android_inputType, InputType.TYPE_CLASS_TEXT)
            hintText = getString(R.styleable.JEditText_android_hint) ?: ""
            text = getString(R.styleable.JEditText_android_text) ?: ""
            var sp = getDimension(R.styleable.JEditText_android_textSize, context.resources.getDimension(R.dimen.sp_14))
            binding.etJ.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp)
            var tailText = getString(R.styleable.JEditText_tailText) ?: ""
            setTailButtonText(tailText, R.drawable.bg_radius_100_tail_button, R.color.color_j_tail_text)
            var tailImg = getResourceId(R.styleable.JEditText_tailImg, 0)
            setTailImageDrawable(tailImg)
            val maxLen = (getString(R.styleable.JEditText_android_maxLength) ?: "0").toInt()
            if (maxLen > 0) {
                binding.etJ.filters = arrayOf(InputFilter.LengthFilter(maxLen))
            } else {
                binding.etJ.filters = arrayOf(InputFilter.LengthFilter(50))
            }
            recycle()
        }
    }
    fun setTailImageDrawable(@DrawableRes drawableRes: Int) {
        if (drawableRes != 0) {
            binding.ivJ.setImageDrawable(ContextCompat.getDrawable(context, drawableRes))
            binding.ivJ.visibility = View.VISIBLE
        }
    }
    fun hideTailImageDrawable() {
        binding.ivJ.visibility = View.GONE
    }
    fun setInputFilter(
        stringInputFilter: InputFilter?,
        lengthInputFilter: InputFilter = InputFilter.LengthFilter(50)
    ) {
        if (stringInputFilter == null) {
            binding.etJ.filters = arrayOf(lengthInputFilter)
        } else {
            binding.etJ.filters = arrayOf(stringInputFilter, lengthInputFilter)
        }
    }
    fun clear() {
        text = ""
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        binding.etJ.addTextChangedListener(textWatcher)
    }

    /**
     * drawableRes
     * textColor
     */
    fun setTailButtonText(text: String, drawableRes: Int, textColor: Int) {
        if (text.isNotEmpty()) {
            binding.tvTail.background = ContextCompat.getDrawable(context, drawableRes)
            binding.tvTail.setTextColor(ContextCompat.getColor(context, textColor))
            binding.tvTail.visibility = View.VISIBLE
        }
    }
    fun hideTailButtonText() {
        binding.tvTail.visibility = View.GONE
    }

    fun setTailButtonEnable(flag: Boolean) {
        binding.tvTail.isEnabled = flag
    }

    fun setTailImgViewEnable(flag: Boolean) {
        binding.ivJ.isEnabled = flag
    }

    fun setTailImgOnClickListener(listener: View.OnClickListener) {
        binding.ivJ.setOnClickListener(listener)
    }
    fun setTailButtonOnClickListener(listener: OnClickListener) {
        binding.tvTail.setOnClickListener(listener)
    }
    fun hideKeyboard() {
        context.controlSoftKeyboard(binding.etJ, false)
    }

    fun showKeyboard() {
        context.controlSoftKeyboard(binding.etJ, true)
    }
    fun setTailButtonSelected(isSelected: Boolean) {
        binding.tvTail.isSelected = isSelected
    }
    fun getTailButtonSelected(): Boolean {
        return binding.tvTail.isSelected
    }
    fun setTailImgSelected(isSelected: Boolean) {
        binding.ivJ.isSelected = isSelected
    }
    fun getTailImgSelected(): Boolean {
        return binding.ivJ.isSelected
    }
    fun setInputType(inputType: Int) {
        binding.etJ.inputType = inputType
    }
}
