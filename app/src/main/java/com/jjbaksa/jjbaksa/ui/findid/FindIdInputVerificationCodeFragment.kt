package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdInputVerificationCodeBinding
import com.jjbaksa.jjbaksa.ui.findid.viewmodel.FindIdViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindIdInputVerificationCodeFragment : Fragment() {
    private lateinit var binding: FragmentFindIdInputVerificationCodeBinding

    private val findIdViewModel: FindIdViewModel by activityViewModels()
    var numberBoxState = mutableListOf<Boolean>(false, false, false, false)
    private val findIdCustomDialog by lazy {
        FindIdCustomDialog()
    }

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
        val prev = parentFragmentManager.findFragmentByTag("find_id_custom_dialog")
        if (prev != null) ft.remove(prev)
        ft.addToBackStack(null)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberBoxState = mutableListOf(false, false, false, false)

        nextToVerificationCodeBox()
        observeData()

        binding.textViewFindIdResendVerificationCode.setOnClickListener {
            Toast.makeText(requireContext(), "인증번호가 재발송 되었습니다.", Toast.LENGTH_SHORT).show()
            findIdViewModel.getAuthEmail(findIdViewModel.userEmail.value.toString())
        }

        binding.buttonFindIdVerificationCode.setOnClickListener {
            findIdViewModel.getUserAccount(
                binding.editTextFindIdVerificationCodeOne.text.toString() +
                    binding.editTextFindIdVerificationCodeTwo.text.toString() +
                    binding.editTextFindIdVerificationCodeThree.text.toString() +
                    binding.editTextFindIdVerificationCodeFour.text.toString()
            )
            if (findIdViewModel.userAccount.value?.isEmpty()!!) {
                binding.textViewFindIdNotCorrectEmailFormat.isVisible = true
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
        findIdViewModel.userAccount.observe(
            viewLifecycleOwner,
            Observer<String> {
                if (it.isNotEmpty()) {
                    findIdCustomDialog.show(parentFragmentManager, "find_id_custom_dialog")
                }
            }
        )
    }
}
