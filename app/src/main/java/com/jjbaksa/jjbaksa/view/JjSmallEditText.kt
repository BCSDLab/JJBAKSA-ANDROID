package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.JjSmallEditTextBinding

typealias EditTextChanged = (Editable?, Int?) -> Unit

open class JjSmallEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: JjSmallEditTextBinding

    private lateinit var textChanged: EditTextChanged
    private lateinit var focusChanged: FocusChanged

    var firstEditTextBackground: Drawable? = ContextCompat.getDrawable(
        context,
        R.drawable.shape_rect_eeeeee_solid_radius_8
    )
        get() = binding.verificationCodeOneEditText.background
        set(value) {
            field = value
            binding.verificationCodeOneEditText.background = value
        }
    var secondEditTextBackground: Drawable? = ContextCompat.getDrawable(
        context,
        R.drawable.shape_rect_eeeeee_solid_radius_8
    )
        get() = binding.verificationCodeTwoEditText.background
        set(value) {
            field = value
            binding.verificationCodeTwoEditText.background = value
        }
    var thirdEditTextBackground: Drawable? = ContextCompat.getDrawable(
        context,
        R.drawable.shape_rect_eeeeee_solid_radius_8
    )
        get() = binding.verificationCodeThreeEditText.background
        set(value) {
            field = value
            binding.verificationCodeThreeEditText.background = value
        }
    var fourthEditTextBackground: Drawable? = ContextCompat.getDrawable(
        context,
        R.drawable.shape_rect_eeeeee_solid_radius_8
    )
        get() = binding.verificationCodeFourEditText.background
        set(value) {
            field = value
            binding.verificationCodeFourEditText.background = value
        }

    init {
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.jj_small_edit_text,
            this,
            true
        )

        setAddTextChangedListener()
    }

    private fun setAddTextChangedListener() {
        binding.verificationCodeOneEditText.addTextChangedListener {
            textChanged.invoke(it, 0)
            if (it?.length == 1) {
                binding.verificationCodeTwoEditText.requestFocus()
            }
        }
        binding.verificationCodeTwoEditText.addTextChangedListener {
            textChanged.invoke(it, 1)
            if (it?.length == 1) {
                binding.verificationCodeThreeEditText.requestFocus()
            }
        }
        binding.verificationCodeThreeEditText.addTextChangedListener {
            textChanged.invoke(it, 2)
            if (it?.length == 1) {
                binding.verificationCodeFourEditText.requestFocus()
            }
        }
        binding.verificationCodeFourEditText.addTextChangedListener {
            textChanged.invoke(it, 3)
        }
    }

    fun addTextChangedListener(textChanged: EditTextChanged) {
        this.textChanged = textChanged
    }

    fun setOutlineEditText(drawable: Drawable?) {
        firstEditTextBackground = drawable
        secondEditTextBackground = drawable
        thirdEditTextBackground = drawable
        fourthEditTextBackground = drawable
    }
}
