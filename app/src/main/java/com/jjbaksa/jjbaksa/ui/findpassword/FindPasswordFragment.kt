package com.jjbaksa.jjbaksa.ui.findpassword

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import com.jjbaksa.jjbaksa.view.JjEditText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPasswordFragment : BaseFragment<FragmentFindPasswordBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password

    private val viewModel: FindPasswordViewModel by activityViewModels()

    private var outlineState = false

    override fun initView() {}

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener {
            requireActivity().finish()
        }
        changeButtonUI()
        sendVerificationCode()
    }

    private fun changeButtonUI() {
        binding.inputIdEditText.also { idEditText ->
            idEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.inputEmailEditText.editTextBackground = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
                    )
                }
            }
            idEditText.addTextChangedListener {
                if (outlineState) {
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
            emailEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.inputEmailEditText.editTextBackground = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
                    )
                }
            }
            emailEditText.addTextChangedListener {
                if (outlineState) {
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

    private fun isVisibleButton(id: String, email: String): Boolean =
        id.isNotEmpty() && email.isNotEmpty()

    private fun sendVerificationCode() {
        binding.buttonFindPasswordSendToInputCode.setOnClickListener {
            if(!isEmailFormatCorrect(binding.inputEmailEditText.editTextText)) {
                KeyboardProvider(requireContext()).hideKeyboard(binding.inputEmailEditText)
                showSnackBar(getString(R.string.incorrect_email_format))
                highlightEditTextOutline(binding.inputEmailEditText)
                normalizeEditTextOutline(binding.inputIdEditText)
                return@setOnClickListener
            }
            viewModel.postUserEmailPassword(
                binding.inputIdEditText.editTextText,
                binding.inputEmailEditText.editTextText
            )
        }
    }

    override fun subscribe() {
        viewModel.toastMsg.observe(viewLifecycleOwner) {
            if (it.contains(USER)) {
                KeyboardProvider(requireContext()).hideKeyboard(binding.inputIdEditText)
                showSnackBar(getString(R.string.fail_id))
                highlightEditTextOutline(binding.inputIdEditText)
                normalizeEditTextOutline(binding.inputEmailEditText)
            } else if (it.contains(EMAIL)) {
                KeyboardProvider(requireContext()).hideKeyboard(binding.inputEmailEditText)
                showSnackBar(getString(R.string.fail_email))
                highlightEditTextOutline(binding.inputEmailEditText)
                normalizeEditTextOutline(binding.inputIdEditText)
            } else {
                showSnackBar(it)
            }
        }
        viewModel.isSuccess.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.getUserInfo(
                    binding.inputIdEditText.editTextText,
                    binding.inputEmailEditText.editTextText
                )
                findNavController().navigate(R.id.action_nav_find_password_to_nav_find_password_input_code)
            } else {
                outlineState = true
                KeyboardProvider(requireContext()).hideKeyboard(binding.inputEmailEditText)
            }
        }
    }

    private fun highlightEditTextOutline(editText: JjEditText) {
        editText.editTextBackground = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rectf6bf54_solid_radius_100_stroke_ff7f23
        )
    }
    private fun normalizeEditTextOutline(editText: JjEditText) {
        editText.editTextBackground = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
        )
    }

    private fun isEmailFormatCorrect(email: String): Boolean {
        val emailFormat = Regex("^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$")
        return emailFormat.matches(email)
    }

    companion object {
        const val USER = "사용자"
        const val EMAIL = "이메일"
    }
}