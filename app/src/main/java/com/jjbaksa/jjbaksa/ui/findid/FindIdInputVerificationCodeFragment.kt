package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdInputVerificationCodeBinding
import com.jjbaksa.jjbaksa.ui.findid.viewmodel.FindIdViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FindIdInputVerificationCodeFragment : Fragment() {
    private lateinit var binding: FragmentFindIdInputVerificationCodeBinding

    private val findIdViewModel: FindIdViewModel by activityViewModels()
    var numberBoxState = mutableListOf<Boolean>(false, false, false, false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_find_id_input_verification_code,
            container,
            false
        )
        val ft = parentFragmentManager.beginTransaction()
        val prev = parentFragmentManager.findFragmentByTag(FIND_ID_DIALOG_TAG)
        if (prev != null) ft.remove(prev)
        ft.addToBackStack(null)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberBoxState = mutableListOf(false, false, false, false)
        binding.jjAppBarContainer.setOnClickListener {
            findNavController().popBackStack()
        }

        nextToVerificationCodeBox()
        reSendVerificationCode()
        findId()
        observeData()
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            requireContext(),
            binding.root,
            msg,
            Snackbar.LENGTH_SHORT
        ).also {
            it.setAction(
                R.string.close,
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        it.dismiss()
                    }
                })
        }.show()
    }

    private fun reSendVerificationCode() {
        binding.textViewFindIdResendVerificationCode.setOnClickListener {
            findIdViewModel.getAuthEmail(findIdViewModel.userEmail.value.toString())
        }
    }

    private fun findId() {
        binding.buttonFindIdVerificationCode.setOnClickListener {
            findIdViewModel.getUserAccount(
                binding.editTextFindIdVerificationCodeOne.text.toString() +
                        binding.editTextFindIdVerificationCodeTwo.text.toString() +
                        binding.editTextFindIdVerificationCodeThree.text.toString() +
                        binding.editTextFindIdVerificationCodeFour.text.toString()
            )
            KeyboardProvider().hideKeyboard(
                requireContext(),
                binding.editTextFindIdVerificationCodeFour
            )
            if (findIdViewModel.userAccount.value?.isEmpty()!!) {
                binding.buttonFindIdVerificationCode.isEnabled = false
            }
        }
    }

    private fun nextToVerificationCodeBox() {
        with(binding) {
            editTextFindIdVerificationCodeOne.addTextChangedListener {
                checkNumberInCodeBox(
                    it?.length!!,
                    0,
                    editTextFindIdVerificationCodeTwo
                )
            }
            editTextFindIdVerificationCodeTwo.addTextChangedListener {
                checkNumberInCodeBox(
                    it?.length!!,
                    1,
                    editTextFindIdVerificationCodeThree
                )
            }
            editTextFindIdVerificationCodeThree.addTextChangedListener {
                checkNumberInCodeBox(
                    it?.length!!,
                    2,
                    editTextFindIdVerificationCodeFour
                )
            }
            editTextFindIdVerificationCodeFour.addTextChangedListener {
                checkNumberInCodeBox(it?.length!!, 3, null)
            }
        }
    }

    private fun checkNumberInCodeBox(
        numberLength: Int,
        pos: Int,
        boxNumber: EditText?
    ) {
        if (numberLength == 1) {
            if (pos != 3) {
                boxNumber?.requestFocus()
            }
            numberBoxState[pos] = true
        } else numberBoxState[pos] = false

        findIdViewModel.updateBoxUiState(numberBoxState)
    }

    private fun onActiveButton(value: Boolean) {
        binding.buttonFindIdVerificationCode.isEnabled = value
    }

    private fun observeData() {
        findIdViewModel.authEmailState.observe(
            viewLifecycleOwner
        ) {
            if (it == RespResult.Success(true)) {
                showSnackBar(getString(R.string.resend_verification_code_content))
            } else {
                showSnackBar("인증번호 재전송 실패")
            }
        }
        findIdViewModel.numberBoxUiState.observe(
            viewLifecycleOwner,
            Observer<MutableList<Boolean>> {
                var checkNumber = 0
                it.forEach { boolData ->
                    if (!boolData) onActiveButton(boolData) else checkNumber++
                }
                if (checkNumber == 4) onActiveButton(true)
            }
        )
        findIdViewModel.userIdInfo.observe(
            viewLifecycleOwner
        ) {
            if (it.isSuccess) {
                FindIdCustomDialog().show(parentFragmentManager, FIND_ID_DIALOG_TAG)
            } else {
                showSnackBar(it.msg)
                lifecycleScope.launch {
                    failVerify()
                }
            }
        }
    }

    private suspend fun failVerify() {
        binding.editTextFindIdVerificationCodeOne.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8_stroke_ff7f23)
        binding.editTextFindIdVerificationCodeTwo.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8_stroke_ff7f23)
        binding.editTextFindIdVerificationCodeThree.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8_stroke_ff7f23)
        binding.editTextFindIdVerificationCodeFour.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8_stroke_ff7f23)
        delay(2000L)
        binding.editTextFindIdVerificationCodeOne.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8)
        binding.editTextFindIdVerificationCodeTwo.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8)
        binding.editTextFindIdVerificationCodeThree.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8)
        binding.editTextFindIdVerificationCodeFour.background =
            requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_8)
    }

    companion object {
        const val FIND_ID_DIALOG_TAG = "find_id_custom_dialog"
    }
}
