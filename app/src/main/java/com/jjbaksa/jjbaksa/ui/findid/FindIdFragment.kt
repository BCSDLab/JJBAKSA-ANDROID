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

    override fun initView() {}

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener {
            requireActivity().finish()
        }
        binding.buttonFindIdSendToVerificationCode.setOnClickListener {
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextFindIdToEmail)
            viewModel.postUserEmailId(binding.editTextFindIdToEmail.text.toString())
        }
        binding.editTextFindIdToEmail.addTextChangedListener {
            binding.editTextFindIdToEmail.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.shape_rect_eeeeee_solid_radius_100_padding_7_11_11_8
            )
            binding.buttonFindIdSendToVerificationCode.isEnabled =
                viewModel.stateButton(it?.length!!)
        }
    }

    override fun subscribe() {
        viewModel.userEmailIdState.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_find_id_to_input_id_verification_code)
            } else {
                binding.buttonFindIdSendToVerificationCode.isEnabled = false
                binding.editTextFindIdToEmail.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.shape_rectf6bf54_solid_radius_100_stroke_ff7f23
                )
            }
        }
        viewModel.toastMsg.observe(viewLifecycleOwner) {
            showSnackBar(it)
            KeyboardProvider(requireContext()).hideKeyboard(binding.editTextFindIdToEmail)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.editTextFindIdToEmail.setText(null)
    }
}
