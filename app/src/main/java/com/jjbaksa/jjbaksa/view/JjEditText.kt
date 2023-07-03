package com.jjbaksa.jjbaksa.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.JjEditTextBinding

typealias TextChanged = (Editable?) -> Unit
typealias FocusChanged = (View, Boolean) -> Unit

open class JjEditText constructor(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private lateinit var binding: JjEditTextBinding

    private lateinit var textChanged: TextChanged
    private lateinit var focusChanged: FocusChanged

    var onButtonClickListener: OnClickListener? = null

    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.JjEditText)
    private var editTextHint = typedArray.getString(R.styleable.JjEditText_hint)
    private var editTextHintColor = typedArray.getColor(R.styleable.JjEditText_hintColor, 0)
    private var editTextHintSize = typedArray.getDimensionPixelSize(
        R.styleable.JjEditText_hintSize, 14
    )
    private var isPassword = typedArray.getBoolean(R.styleable.JjEditText_is_password, false)
    private var isEmail = typedArray.getBoolean(R.styleable.JjEditText_is_email, false)
    private var hasTitle = typedArray.getBoolean(R.styleable.JjEditText_has_title, false)
    private var hasButton = typedArray.getBoolean(R.styleable.JjEditText_has_button, false)
    private var editTextGravity = typedArray.getInt(R.styleable.JjEditText_editText_gravity, 0x03)
    private var title = typedArray.getString(R.styleable.JjEditText_title)
    private var titleSize = typedArray.getDimensionPixelSize(
        R.styleable.JjEditText_titleSize, 14
    )

    private var buttonText = typedArray.getString(R.styleable.JjEditText_button_text)
    private var buttonTextSize = typedArray.getDimensionPixelSize(
        R.styleable.JjEditText_button_textSize, 14
    )

    private var showPasswordConfirm = false

    var isButtonEnabled = true
        set(value) {
            field = value
            binding.buttonJjEditTextButton.isEnabled = value
        }

    var editTextText: String = ""
        get() = binding.editTextJjEditTextInput.text.toString()
        set(value) {
            field = value
            binding.editTextJjEditTextInput.setText(value)
        }

    var editTextBackground: Drawable? = ContextCompat.getDrawable(
        context,
        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
    )
        get() = binding.editTextJjEditTextInput.background
        set(value) {
            field = value
            binding.editTextJjEditTextInput.background = value
        }

    init {
        initView()
    }

    private fun initView() {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.jj_edit_text,
            this,
            true
        )

        binding.editTextJjEditTextInput.background = editTextBackground
        binding.editTextJjEditTextInput.hint = editTextHint
        binding.editTextJjEditTextInput.setHintTextColor(editTextHintColor)
        binding.editTextJjEditTextInput.setTextSize(
            TypedValue.COMPLEX_UNIT_PX, editTextHintSize.toFloat()
        )
        binding.editTextJjEditTextInput.setText(editTextText)

        isPasswordField()
        isEmailField()

        setViewTitle()
        hasButton()
        setEditTextGravity()

        setAddTextChangedListener()
        setOnFocusChangeListener()
    }

    private fun setAddTextChangedListener() {
        binding.editTextJjEditTextInput.addTextChangedListener {
            textChanged.invoke(it)
        }
    }

    private fun setOnFocusChangeListener() {
        binding.editTextJjEditTextInput.setOnFocusChangeListener { view, hasFocus ->
            focusChanged.invoke(view, hasFocus)
        }
    }

    private fun setViewTitle() {
        if (hasTitle) {
            binding.textViewJjEditTextTitle.text = title

            binding.textViewJjEditTextTitle.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                titleSize.toFloat()
            )
        }
    }

    private fun setEditTextGravity() {
        binding.editTextJjEditTextInput.gravity = editTextGravity
    }

    private fun hasButton() {
        if (hasButton) {
            binding.buttonJjEditTextButton.visibility = View.VISIBLE

            binding.buttonJjEditTextButton.text = buttonText
            binding.buttonJjEditTextButton.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                buttonTextSize.toFloat()
            )

            binding.buttonJjEditTextButton.setOnClickListener {
                onButtonClickListener?.onClick(it)
            }
        }
    }

    private fun isEmailField() {
        if (isEmail) {
            binding.editTextJjEditTextInput.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
    }

    private fun isPasswordField() {
        if (isPassword) {
            binding.imageButtonJjEditTextPasswordHide.visibility = View.VISIBLE
            binding.editTextJjEditTextInput.transformationMethod =
                PasswordTransformationMethod.getInstance()

            binding.imageButtonJjEditTextPasswordHide.setOnClickListener {
                showPasswordConfirm = !showPasswordConfirm
                if (showPasswordConfirm) {
                    binding.imageButtonJjEditTextPasswordHide.setImageResource(R.drawable.sel_jj_edit_text_password_show_checked)
                    binding.editTextJjEditTextInput.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                } else {
                    binding.imageButtonJjEditTextPasswordHide.setImageResource(R.drawable.sel_jj_edit_text_password_show_unchecked)
                    binding.editTextJjEditTextInput.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                }
            }
        }
    }

    fun addTextChangedListener(textChanged: TextChanged) {
        this.textChanged = textChanged
    }

    fun setOnFocusChangeListener(focusChanged: FocusChanged) {
        this.focusChanged = focusChanged
    }

    fun

    interface OnClickListener {
        fun onClick(view: View)
    }

    inline fun setOnClickListener(crossinline onClick: (View) -> Unit) {
        this.onButtonClickListener = object : OnClickListener {
            override fun onClick(view: View) {
                onClick(view)
            }
        }
    }
}
