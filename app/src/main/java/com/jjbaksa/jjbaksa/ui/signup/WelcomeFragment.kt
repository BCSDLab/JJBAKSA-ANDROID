package com.jjbaksa.jjbaksa.ui.signup

import android.annotation.SuppressLint
import android.text.Editable
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.google.android.material.internal.TextWatcherAdapter
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentWelcomeBinding
import com.jjbaksa.jjbaksa.ui.signup.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {

    private val signUpViewModel: SignUpViewModel by activityViewModels()
    override val layoutId: Int
        get() = R.layout.fragment_welcome
    override fun initView() {}

    override fun initEvent() {

        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        }
        )

        with(binding) {

            binding.jEditTextWelcomeName.addTextChangedListener(
                @SuppressLint("RestrictedApi")
                object : TextWatcherAdapter() {
                    override fun afterTextChanged(p0: Editable) {

                        if (p0?.isNotEmpty() == true) {
                            signUpViewModel.nickname = p0.toString()
                        }
                        buttonWelcomeComplete.isEnabled = p0?.isNotEmpty() == true
                    }
                }
            )

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

    override fun subscribe() {}

    override fun onResume() {
        super.onResume()
        if (signUpViewModel.nickname.isNotEmpty()) {
            binding.jEditTextWelcomeName.text = signUpViewModel.id
        }
    }
}
