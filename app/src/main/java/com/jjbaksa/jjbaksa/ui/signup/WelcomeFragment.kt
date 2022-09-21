package com.jjbaksa.jjbaksa.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

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
        }

        binding.buttonWelcomeComplete.setOnClickListener {
            activity?.finish()
        }

        return binding.root
    }
}
