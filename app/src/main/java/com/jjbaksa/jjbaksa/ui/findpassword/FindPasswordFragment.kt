package com.jjbaksa.jjbaksa.ui.findpassword

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jjbaksa.domain.base.RespResult
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseFragment
import com.jjbaksa.jjbaksa.databinding.FragmentFindPasswordBinding
import com.jjbaksa.jjbaksa.ui.findpassword.viewmodel.FindPasswordViewModel
import com.jjbaksa.jjbaksa.util.RegexUtil

class FindPasswordFragment():BaseFragment<FragmentFindPasswordBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_find_password

    private val findPasswordViewModel: FindPasswordViewModel by activityViewModels()

    override fun initView() {}

    override fun initEvent() {
        observeData()

        with(binding.editTextFindPasswordToEmail){
            addTextChangedListener {
                binding.buttonFindPasswordSendToInputCode.isEnabled = this.text.isNotEmpty()
            }
        }

        binding.buttonFindPasswordSendToInputCode.setOnClickListener {
            with(binding.editTextFindPasswordToEmail.text.toString()){
                if (RegexUtil.checkEmailFormat(this)){
                    findPasswordViewModel.getAuthEmail(this)
                } else {
                    failEmailCheck()
                }
            }
        }
    }

    override fun subscribe() {}

    override fun onStart() {
        super.onStart()
        binding.editTextFindPasswordToEmail.setText(null)
    }

    private fun observeData(){
        findPasswordViewModel.authEmailState.observe(
            viewLifecycleOwner,
            Observer<RespResult<Boolean>>{
                if (it == RespResult.Success(true)){
                    findNavController().navigate(R.id.action_nav_find_password_to_nav_find_password_input_code)
                } else {
                    failEmailCheck()
                }
            }
        )
    }

    private fun failEmailCheck(){
        binding.layerFindPasswordWarningContent.visibility = View.VISIBLE
        binding.buttonFindPasswordSendToInputCode.isEnabled = false
        binding.editTextFindPasswordToEmail.setBackgroundResource(R.drawable.shape_rect_eeeeee_solid_radius_100_stroke_ff7f23)
    }
}