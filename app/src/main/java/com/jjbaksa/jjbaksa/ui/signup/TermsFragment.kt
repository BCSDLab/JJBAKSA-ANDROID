package com.jjbaksa.jjbaksa.ui.signup

import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentTermsBinding

class TermsFragment : BaseFragment<FragmentTermsBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_terms
    override fun initView() {}
    override fun initEvent() {

        with(binding) {
            jjCheckBoxTermsTermsOne.setOnClickListener {
                isAllCheckBoxChecked()
            }
            jjCheckBoxTermsTermsTwo.setOnClickListener {
                isAllCheckBoxChecked()
            }
            jjCheckBoxTermsTermsCheckAll.setOnClickListener {
                checkAllCheckBox(jjCheckBoxTermsTermsCheckAll.isChecked)
                isAllCheckBoxChecked()
            }
            buttonTermsNext.setOnClickListener {
                findNavController().navigate(R.id.action_nav_graph_move_to_register)
            }
        }
    }

    override fun subscribe() {}

    override fun onResume() {
        super.onResume()
        checkAllCheckBox(false)
        isAllCheckBoxChecked()
    }
    private fun isAllCheckBoxChecked() {
        val isAllCheckBoxChecked =
            binding.jjCheckBoxTermsTermsOne.isChecked && binding.jjCheckBoxTermsTermsTwo.isChecked

        binding.jjCheckBoxTermsTermsCheckAll.isChecked = isAllCheckBoxChecked
        binding.buttonTermsNext.isEnabled = isAllCheckBoxChecked
    }

    private fun checkAllCheckBox(isChecked: Boolean) {
        binding.jjCheckBoxTermsTermsOne.isChecked = isChecked
        binding.jjCheckBoxTermsTermsTwo.isChecked = isChecked
    }
}
