package com.jjbaksa.jjbaksa.ui.findpassword

import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordResetBinding
import com.jjbaksa.jjbaksa.util.EMAIL_REGEX
import com.jjbaksa.jjbaksa.util.RegexUtil.isPasswordRuleMatch
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

class FindPasswordResetFragment: BaseFragment<FragmentFindPasswordResetBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password_reset

    private var showIconState = mutableListOf<Boolean>(false,false)

    override fun initView() {
    }

    override fun initEvent() {
        onVisiblePassword(binding.imageViewNewPasswordShow, binding.editTextNewPassword, 0)
        onVisiblePassword(binding.imageViewResetPasswordShow, binding.editTextCheckPassword, 0)
    }

    override fun subscribe() {
    }

    private fun onVisiblePassword(showIcon:ImageView, editText:EditText, pos:Int){
        showIcon.setOnClickListener {
            showIconState[pos] = !showIconState[pos]
            if (showIconState[pos]){
                showIcon.setImageResource(R.drawable.sel_jj_edit_text_password_show_checked)
                editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                editText.setSelection(editText.text.length)
            } else {
                showIcon.setImageResource(R.drawable.sel_jj_edit_text_password_show_unchecked)
                editText.inputType = InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
                editText.setSelection(editText.text.length)
            }
        }
    }
}