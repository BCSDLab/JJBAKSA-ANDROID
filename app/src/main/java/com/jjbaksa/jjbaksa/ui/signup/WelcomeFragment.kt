package com.jjbaksa.jjbaksa.ui.signup

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentWelcomeBinding
import com.jjbaksa.jjbaksa.ui.signup.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(){

    private val signUpViewModel: SignUpViewModel by activityViewModels()
    override val layoutId: Int
        get() = R.layout.fragment_welcome
    override fun initView() {}

    override fun initEvent() {

        with(binding) {
            jEditTextWelcomeName.run {
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        // 이전 텍스트 변경 전에 수행할 동작
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        // 텍스트가 변경될 때 수행할 동작
                        signUpViewModel.nickname=jEditTextWelcomeName.text.toString()
                    }

                    override fun afterTextChanged(p0: Editable?) {

                        if (p0?.isNotEmpty() == true) {
                            signUpViewModel.nickname = p0.toString()
                        }
                        buttonWelcomeComplete.isEnabled = p0?.isNotEmpty() == true
                    }
                })

                buttonWelcomeComplete.setOnClickListener {

                    signUpViewModel.signUpRequest()
                    signUpViewModel.isSignUpSuccess.observe(viewLifecycleOwner) {
                        if (it) {
                            activity?.finish()
                        }
                    }
                }
            }

        }
    }


    override fun subscribe() {}

    override fun onResume() {
        super.onResume()
        if (signUpViewModel.nickname.isNotEmpty()) {
            binding.jEditTextWelcomeName.text=signUpViewModel.id
        }
    }
}
