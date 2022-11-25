package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdBinding
import java.util.regex.Pattern

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
            if (checkEmailFormat()){
                // Email format is OK
//                binding.editTextFindIdToEmail.setText(null)
                findNavController().navigate(R.id.action_find_id_to_input_id_verification_code)
            } else {
                // Email format is Fail
                binding.textViewFindIdNotCorrectEmailFormat.visibility = View.VISIBLE
                binding.buttonFindIdSendToVerificationCode.isEnabled = false
            }
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        with(binding){
            textViewFindIdNotCorrectEmailFormat.visibility = View.INVISIBLE
            buttonFindIdSendToVerificationCode.isEnabled = false
            editTextFindIdToEmail.setText(null)
        }
    }

    private fun inputEmailAddress(){
        binding.editTextFindIdToEmail.addTextChangedListener {
            if (it?.length!! > 0){
                binding.buttonFindIdSendToVerificationCode.isEnabled = true
            }
        }
    }

    private fun checkEmailFormat(): Boolean{
        val emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        var email = binding.editTextFindIdToEmail.text.toString().trim()
        return Pattern.matches(emailValidation, email)
    }
}