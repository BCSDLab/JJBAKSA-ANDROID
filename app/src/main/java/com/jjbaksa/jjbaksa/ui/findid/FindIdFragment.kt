package com.jjbaksa.jjbaksa.ui.findid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdBinding
import com.jjbaksa.jjbaksa.ui.findid.viewmodel.FindIdViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider

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
            binding.editTextFindIdToEmail.background =
                requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8)
            binding.buttonFindIdSendToVerificationCode.isEnabled =
                findIdViewModel.stateButton(it?.length!!)
        }

        back()
        sendVerificationCode()
        observeData()
    }

    override fun onStart() {
        super.onStart()
        binding.editTextFindIdToEmail.setText(null)
    }

    private fun back() {
        binding.jjAppBarContainer.setOnClickListener { requireActivity().finish() }
    }

    private fun sendVerificationCode() {
        binding.buttonFindIdSendToVerificationCode.setOnClickListener {
            KeyboardProvider().hideKeyboard(requireContext(), binding.editTextFindIdToEmail)
            findIdViewModel.getAuthEmail(binding.editTextFindIdToEmail.text.toString())
        }
    }

    private fun showSnackBar() {
        Snackbar.make(
            requireContext(),
            binding.root,
            getString(R.string.not_correct_email_format_and_not_exist_account),
            Snackbar.LENGTH_SHORT
        ).also {
            it.setAction(
                R.string.close,
                object : OnClickListener {
                    override fun onClick(v: View?) {
                        it.dismiss()
                    }
                }
            )
        }.show()
    }

    private fun observeData() {
        findIdViewModel.authEmailState.observe(
            viewLifecycleOwner,
            Observer<RespResult<Boolean>> {
                if (it == RespResult.Success(true)) {
                    findNavController().navigate(R.id.action_find_id_to_input_id_verification_code)
                } else {
                    showSnackBar()
                    binding.buttonFindIdSendToVerificationCode.isEnabled = false
                    binding.editTextFindIdToEmail.background =
                        requireActivity().getDrawable(R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
                }
            }
        )
    }
}
