package com.jjbaksa.jjbaksa.ui.withdrawal

import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityWithdrawalBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.ui.withdrawal.viewmodel.WithdrawalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalActivity : BaseActivity<ActivityWithdrawalBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_withdrawal
    private val viewModel: WithdrawalViewModel by viewModels()

    override fun initView() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.getNickname()
    }

    override fun subscribe() {}

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
        withdrawal()
        setWithdrawalReason()
        setFocusInputField()
        setInputField()
    }

    private fun withdrawal() {
        binding.withdrawalButton.setOnClickListener {
            showCompleteWithdrawalDialog()
            Log.d("로그", "${viewModel.reason.value} / ${binding.inputEditTextField.text }")
        }
    }

    private fun setWithdrawalReason() {
        binding.reasonWithdrawalRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.reason_withdrawal_radio_button_1 -> {
                    viewModel.setWithdrawalReason(binding.reasonWithdrawalRadioButton1.text.toString())
                }

                R.id.reason_withdrawal_radio_button_2 -> {
                    viewModel.setWithdrawalReason(binding.reasonWithdrawalRadioButton2.text.toString())
                }

                R.id.reason_withdrawal_radio_button_3 -> {
                    viewModel.setWithdrawalReason(binding.reasonWithdrawalRadioButton3.text.toString())
                }

                R.id.reason_withdrawal_radio_button_4 -> {
                    viewModel.setWithdrawalReason(binding.reasonWithdrawalRadioButton4.text.toString())
                }

                R.id.reason_withdrawal_radio_button_5 -> {
                    viewModel.setWithdrawalReason(binding.reasonWithdrawalRadioButton5.text.toString())
                }
            }
        }
    }

    private fun setFocusInputField() {
        binding.inputEditTextField.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.inputEditTextField.text.toString() == getString(R.string.free_write)) {
                    binding.inputEditTextField.text?.clear()
                }
            }
        }
    }

    private fun setInputField() {
        binding.inputEditTextField.addTextChangedListener {
            viewModel.setInputTextLength(it?.length.toString())

            if (it?.length == 150) {
                viewModel.setMaxInputTextLength(true)
            } else {
                viewModel.setMaxInputTextLength(false)
            }

            if (!it.isNullOrEmpty()) {
                viewModel.setIsEnabled(true)
            } else {
                viewModel.setIsEnabled(false)
            }
        }
    }

    private fun showCompleteWithdrawalDialog() {
        ConfirmDialog(
            getString(R.string.complete_withdrawal),
            getString(R.string.complete_withdrawal_content),
            getString(R.string.close),
            { it.dismiss() },
            "#222222"
        ).show(supportFragmentManager, WITHDRAWAL_DIALOG_TAG)
    }

    companion object {
        const val WITHDRAWAL_DIALOG_TAG = "WITHDRAWAL_DIALOG_TAG"
    }
}
