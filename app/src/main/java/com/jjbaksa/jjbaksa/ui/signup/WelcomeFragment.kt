package com.jjbaksa.jjbaksa.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentWelcomeBinding
import com.jjbaksa.jjbaksa.ui.signup.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    private val signUpViewModel: SignUpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_welcome, container, false
        )

        binding.jjEditTextWelcomeName.addTextChangedListener {
            binding.buttonWelcomeComplete.isEnabled = it?.isNotEmpty() == true

            if (it?.isNotEmpty() == true) {
                signUpViewModel.nickname = it.toString()
            }
        }

        binding.jjEditTextWelcomeName.setOnFocusChangeListener { _, _ -> }

        binding.buttonWelcomeComplete.setOnClickListener {
            signUpViewModel.signUpRequest()
            signUpViewModel.isSignUpSuccess.observe(viewLifecycleOwner) {
                if (it) {
                    activity?.finish()
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (signUpViewModel.nickname.isNotEmpty()) {
            binding.jjEditTextWelcomeName.editTextText = signUpViewModel.id
        }
    }
}
