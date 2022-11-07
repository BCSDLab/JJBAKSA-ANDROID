package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdBinding

class FindIdFragment: Fragment() {
    private lateinit var binding: FragmentFindIdBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_find_id, container, false)

        inputEmailAddress()

        binding.buttonFindIdSendToVerificationCode.setOnClickListener {
            findNavController().navigate(R.id.action_find_id_to_input_id_verification_code)
        }
        return binding.root
    }

    private fun inputEmailAddress(){
        binding.editTextFindIdToEmail.addTextChangedListener {
            binding.buttonFindIdSendToVerificationCode.isEnabled = true
        }
    }
}