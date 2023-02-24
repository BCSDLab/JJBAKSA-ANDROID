package com.jjbaksa.jjbaksa.ui.findpassword

import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.base.ErrorType
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.RegexUtil

class FindPasswordFragment():BaseFragment<FragmentFindPasswordBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password

    private val findPasswordViewModel: FindPasswordViewModel by activityViewModels()

    override fun initView() {}

    override fun initEvent() {
        observeData()
        onEnabledButton()
        onClickButton()
    }

    override fun subscribe() {}

    override fun onStart() {
        super.onStart()
        binding.editTextFindPasswordToAccount.setText(null)
    }

    private fun onEnabledButton(){
        with(binding.editTextFindPasswordToAccount){
            addTextChangedListener {
                binding.buttonFindPasswordSendToInputCode.isEnabled = this.text.isNotEmpty()
            }
        }
    }

    private fun onClickButton(){
        binding.buttonFindPasswordSendToInputCode.setOnClickListener {
            findPasswordViewModel.isExistAccount(binding.editTextFindPasswordToAccount.text.toString())
        }
    }

    private fun observeData(){
        findPasswordViewModel.existAccount.observe(
            viewLifecycleOwner,
            Observer<RespResult<Boolean>>{
                if (it == RespResult.Error<ErrorType>(ErrorType(ERROR_MESSAGE, CODE))){
                    findPasswordViewModel.userAccount.value = binding.editTextFindPasswordToAccount.text.toString()
                    findNavController().navigate(R.id.action_nav_find_password_to_nav_find_password_input_code)
                } else {
                    binding.layerFindPasswordWarningContent.visibility = View.VISIBLE
                }
            }
        )
    }

    companion object {
        const val ERROR_MESSAGE = "이미 존재하는 아이디입니다."
        const val CODE = 11
    }
}