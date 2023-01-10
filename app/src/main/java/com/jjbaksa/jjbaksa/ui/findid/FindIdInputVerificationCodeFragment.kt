package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
class FindIdInputVerificationCodeFragment: Fragment() {
    private lateinit var binding: FragmentFindIdInputVerificationCodeBinding

    private val findIdViewModel: FindIdViewModel by activityViewModels()
    var numberBoxState = mutableListOf<Boolean>(false,false,false,false)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_find_id_input_verification_code, container, false)

        numberBoxState = mutableListOf(false,false,false,false)

        nextToVerificationCodeBox()
        observeData()

        binding.textViewFindIdResendVerificationCode.setOnClickListener {
            Toast.makeText(requireContext(), "인증번호가 재발송 되었습니다.", Toast.LENGTH_SHORT).show()
            findIdViewModel.getFindIdNumberCode(
                findIdViewModel.userEmail.value.toString(),
                null,
                null
            )
        }

        binding.buttonFindIdVerificationCode.setOnClickListener {
            findIdViewModel.getFindId(
                binding.editTextFindIdVerificationCodeOne.text.toString(),
                binding.editTextFindIdVerificationCodeTwo.text.toString(),
                binding.editTextFindIdVerificationCodeThree.text.toString(),
                binding.editTextFindIdVerificationCodeFour.text.toString(),
                FindIdCustomDialog(requireContext()),
                binding.textViewFindIdNotCorrectEmailFormat,
                binding.buttonFindIdVerificationCode,
            )
        }

        return binding.root
    }


    private fun nextToVerificationCodeBox(){
        with(binding){
            editTextFindIdVerificationCodeOne.addTextChangedListener{
                findIdViewModel.checkNumberInCodeBox(it?.length!!, numberBoxState, 0, editTextFindIdVerificationCodeTwo)
            }
            editTextFindIdVerificationCodeTwo.addTextChangedListener {
                findIdViewModel.checkNumberInCodeBox(it?.length!!, numberBoxState, 1, editTextFindIdVerificationCodeThree)
            }
            editTextFindIdVerificationCodeThree.addTextChangedListener {
                findIdViewModel.checkNumberInCodeBox(it?.length!!, numberBoxState, 2, editTextFindIdVerificationCodeFour)
            }
            editTextFindIdVerificationCodeFour.addTextChangedListener {
                findIdViewModel.checkNumberInCodeBox(it?.length!!, numberBoxState, 3, null)
            }
        }
    }

    private fun onActiveButton(value: Boolean){
        binding.buttonFindIdVerificationCode.isEnabled = value
    }

    private fun observeData(){
        findIdViewModel.numberBoxUiState.observe(viewLifecycleOwner, Observer<MutableList<Boolean>>{
            var checkNumber = 0
            it.forEach { boolData ->
                if (!boolData) onActiveButton(boolData) else checkNumber ++
            }
            if (checkNumber == 4) onActiveButton(true)
        })
    }

}