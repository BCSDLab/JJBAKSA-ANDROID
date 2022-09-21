package com.jjbaksa.jjbaksa.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.databinding.FragmentTermsBinding

class TermsFragment : Fragment() {

    private lateinit var binding: FragmentTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_terms, container, false)

        binding.jjCheckBoxTermsTermsOne.setOnClickListener {
            isAllCheckBoxChecked()
        }

        binding.jjCheckBoxTermsTermsTwo.setOnClickListener {
            isAllCheckBoxChecked()
        }

        binding.jjCheckBoxTermsTermsCheckAll.setOnClickListener {
            checkAllCheckBox(binding.jjCheckBoxTermsTermsCheckAll.isChecked)
            isAllCheckBoxChecked()
        }

        binding.buttonTermsNext.setOnClickListener {
            findNavController().navigate(R.id.action_nav_graph_move_to_register)
        }

        return binding.root
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
