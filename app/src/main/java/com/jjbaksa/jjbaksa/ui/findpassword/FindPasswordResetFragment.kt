package com.jjbaksa.jjbaksa.ui.findpassword

import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordResetBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.RegexUtil.isPasswordRuleMatch

class FindPasswordResetFragment : BaseFragment<FragmentFindPasswordResetBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password_reset

    private val findPasswordViewModel: FindPasswordViewModel by activityViewModels()

    private val findPasswordCustomDialog by lazy {
        FindPasswordCustomDialog()
    }

    private var showIconState = mutableListOf<Boolean>(false, false)
    private var buttonEnabled = mutableListOf<Boolean>(false, false)

    override fun initView() {
        val ft = parentFragmentManager.beginTransaction()
        val prev = parentFragmentManager.findFragmentByTag("find_password_custom_dialog")
        if (prev != null) ft.remove(prev)
        ft.addToBackStack(null)
    }

    override fun initEvent() {
        backPressed(binding.jjAppBarContainer, requireActivity(), true)

//        observeDate()
//        onVisiblePassword(binding.imageViewNewPasswordShow, binding.editTextNewPassword, 0)
//        onVisiblePassword(binding.imageViewResetPasswordShow, binding.editTextCheckPassword, 0)
//        onButtonEnabled()
//        onClickButton()
    }

    override fun subscribe() {
    }

//    private fun onVisiblePassword(showIcon: ImageView, editText: EditText, pos: Int) {
//        showIcon.setOnClickListener {
//            showIconState[pos] = !showIconState[pos]
//            if (showIconState[pos]) {
//                showIcon.setImageResource(R.drawable.sel_jj_edit_text_password_show_checked)
//                editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
//                editText.setSelection(editText.text.length)
//            } else {
//                showIcon.setImageResource(R.drawable.sel_jj_edit_text_password_show_unchecked)
//                editText.inputType =
//                    InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD
//                editText.setSelection(editText.text.length)
//            }
//        }
//    }

//    private fun onButtonEnabled() {
//        binding.editTextNewPassword.addTextChangedListener {
//            buttonEnabled[0] = it.toString().isNotEmpty()
//            findPasswordViewModel.getButtonEnabled(buttonEnabled)
//        }
//        binding.editTextCheckPassword.addTextChangedListener {
//            buttonEnabled[1] = it.toString().isNotEmpty()
//            findPasswordViewModel.getButtonEnabled(buttonEnabled)
//        }
//    }

//    private fun onClickButton() {
//        binding.buttonResetPassword.setOnClickListener {
//            if (!binding.editTextNewPassword.text.toString()
//                .isPasswordRuleMatch() || !binding.editTextCheckPassword.text.toString()
//                    .isPasswordRuleMatch()
//            ) {
//                binding.textViewResetPasswordNotCorrectPassword.text =
//                    getString(R.string.not_correct_password_format)
//                binding.layerResetPasswordWarningContent.visibility = View.VISIBLE
//                binding.editTextNewPassword.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
//                binding.editTextCheckPassword.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
//            } else if (binding.editTextNewPassword.text.toString() != binding.editTextCheckPassword.text.toString()) {
//                binding.textViewResetPasswordNotCorrectPassword.text =
//                    getString(R.string.not_correct_password)
//                binding.layerResetPasswordWarningContent.visibility = View.VISIBLE
//                binding.editTextNewPassword.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
//                binding.editTextCheckPassword.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
//            } else {
//                // success change password
//                findPasswordViewModel.setNewPassword(binding.editTextNewPassword.text.toString())
//            }
//        }
//    }

//    private fun observeDate() {
//        findPasswordViewModel.buttonEnabled.observe(
//            viewLifecycleOwner,
//            Observer<MutableList<Boolean>> {
//                binding.buttonResetPassword.isEnabled = !it.contains(false)
//            }
//        )
//        findPasswordViewModel.isChangePassword.observe(
//            viewLifecycleOwner,
//            Observer<Boolean> {
//                if (it) {
//                    findPasswordCustomDialog.show(parentFragmentManager, "find_password_custom_dialog")
//                }
//            }
//        )
//    }
}
