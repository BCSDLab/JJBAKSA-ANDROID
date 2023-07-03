package com.jjbaksa.jjbaksa.ui.findpassword

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.core.content.ContextCompat
import androidx.core.view.isNotEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.domain.resp.user.FormatResp
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import com.jjbaksa.jjbaksa.view.JjEditText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FindPasswordFragment() : BaseFragment<FragmentFindPasswordBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password

    private val findPasswordViewModel: FindPasswordViewModel by activityViewModels()

    private var outlineState = false

    override fun initView() {
        changeButtonUI()
    }

    private fun changeButtonUI() {
        binding.inputIdEditText.also { idEditText ->
            idEditText.setOnFocusChangeListener{ _, hasFocus ->
                if (hasFocus) {
                    binding.inputEmailEditText.editTextBackground = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
                    )
                }
            }
            idEditText.addTextChangedListener {
                if (outlineState) {
                    Log.d("로그", "id 테두리 변경")
                    binding.inputIdEditText.editTextBackground = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
                    )
                    outlineState = false
                }
                binding.buttonFindPasswordSendToInputCode.also { button ->
                    button.isSelected = isVisibleButton(
                        id = it.toString(),
                        email = binding.inputIdEditText.editTextText
                    )
                    button.isEnabled = isVisibleButton(
                        id = it.toString(),
                        email = binding.inputIdEditText.editTextText
                    )
                }
            }
        }
        binding.inputEmailEditText.also { emailEditText ->
            emailEditText.setOnFocusChangeListener{ _, hasFocus ->
                if (hasFocus) {
                    binding.inputEmailEditText.editTextBackground = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
                    )
                }
            }
            emailEditText.addTextChangedListener {
                if (outlineState) {
                    Log.d("로그", "email 테두리 변경")
                    binding.inputEmailEditText.editTextBackground = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
                    )
                    outlineState = false
                }
                binding.buttonFindPasswordSendToInputCode.also { button ->
                    button.isSelected = isVisibleButton(
                        id = binding.inputIdEditText.editTextText,
                        email = it.toString()
                    )
                    button.isEnabled = isVisibleButton(
                        id = binding.inputIdEditText.editTextText,
                        email = it.toString()
                    )
                }
            }
        }
    }
    private fun isVisibleButton(id:String , email:String): Boolean =
        id.isNotEmpty() && email.isNotEmpty()

    override fun initEvent() {
        backPressed(binding.jjAppBarContainer, requireActivity(), false)
        sendVerificationCode()
        observeData()
    }

    private fun sendVerificationCode() {
        binding.buttonFindPasswordSendToInputCode.setOnClickListener {
            findPasswordViewModel.getPasswordVerificationCode(
                binding.inputIdEditText.editTextText,
                binding.inputEmailEditText.editTextText
            )
        }
    }

    override fun subscribe() {}

    override fun onStart() {
        super.onStart()
    }

    private fun observeData() {
        findPasswordViewModel.isPasswordVerificationCode.observe(
            viewLifecycleOwner,
            Observer<FormatResp> {
                if (it.isSuccess) {
                    findNavController().navigate(R.id.action_nav_find_password_to_nav_find_password_input_code)
                } else {
                    outlineState = true
                    KeyboardProvider().hideKeyboard(requireContext(), binding.inputEmailEditText)
                    if (it.msg.toString().contains("사용자")){
                        showSnackBar(requireContext(), getString(R.string.fail_id))
                        changeEditTextOutlineColor(binding.inputIdEditText)
                    } else if (it.msg.toString().contains("이메일")){
                        showSnackBar(requireContext(), getString(R.string.fail_email))
                        changeEditTextOutlineColor(binding.inputEmailEditText)
                    } else {
                        showSnackBar(requireContext(), it.msg.toString())
                    }
                }
            }
        )
    }

    private fun changeEditTextOutlineColor(editText: JjEditText) {
        editText.editTextBackground = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23
        )
    }
}
