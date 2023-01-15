package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdBinding
import com.jjbaksa.jjbaksa.ui.findid.viewmodel.FindIdViewModel
import com.jjbaksa.jjbaksa.util.RegexUtil

class FindIdFragment : Fragment() {
    private lateinit var binding: FragmentFindIdBinding

    private val findIdViewModel: FindIdViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_find_id, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextFindIdToEmail.addTextChangedListener {
            binding.buttonFindIdSendToVerificationCode.isEnabled =
                findIdViewModel.stateButton(it?.length!!)
        }

        binding.buttonFindIdSendToVerificationCode.setOnClickListener {
            if (RegexUtil.checkEmailFormat(binding.editTextFindIdToEmail.text.toString())) {
                // Email format is OK
                findIdViewModel.getFindIdNumberCode(
                    binding.editTextFindIdToEmail.text.toString(),
                    findNavController(),
                    R.id.action_find_id_to_input_id_verification_code
                )
            } else {
                // Email format is Fail
                binding.textViewFindIdNotCorrectEmailFormat.visibility = View.VISIBLE
                binding.buttonFindIdSendToVerificationCode.isEnabled = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            textViewFindIdNotCorrectEmailFormat.visibility = View.INVISIBLE
            buttonFindIdSendToVerificationCode.isEnabled = false
            editTextFindIdToEmail.setText(null)
        }
    }
}
