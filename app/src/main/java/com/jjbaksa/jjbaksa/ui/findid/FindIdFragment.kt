package com.jjbaksa.jjbaksa.ui.findid

import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindIdBinding
import com.jjbaksa.jjbaksa.ui.findid.viewmodel.FindIdViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider

class FindIdFragment : BaseFragment<FragmentFindIdBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_id

    private val viewModel: FindIdViewModel by activityViewModels()

    override fun initView() {
        binding.editTextFindIdToEmail.addTextChangedListener {
            binding.editTextFindIdToEmail.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
            )
            binding.buttonFindIdSendToVerificationCode.isEnabled =
                viewModel.stateButton(it?.length!!)
        }
    }

    override fun initEvent() {
        backPressed(binding.jjAppBarContainer, requireActivity(), false)
        sendVerificationCode()
        observeData()
    }

    override fun subscribe() {}

    override fun onStart() {
        super.onStart()
        binding.editTextFindIdToEmail.setText(null)
    }

    private fun sendVerificationCode() {
        binding.buttonFindIdSendToVerificationCode.setOnClickListener {
            KeyboardProvider().hideKeyboard(requireContext(), binding.editTextFindIdToEmail)
            viewModel.getAuthEmail(binding.editTextFindIdToEmail.text.toString())
        }
    }

    private fun observeData() {
        viewModel.authEmailState.observe(
            viewLifecycleOwner
        ) {
            if (it.isSuccess) {
                findNavController().navigate(R.id.action_find_id_to_input_id_verification_code)
            } else {
                showSnackBar(it.msg.toString())
                binding.buttonFindIdSendToVerificationCode.isEnabled = false
                binding.editTextFindIdToEmail.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rectf6bf54_solid_radius_100_stroke_ff7f23
                )
            }
        }
    }
}
