package com.jjbaksa.jjbaksa.ui.withdrawal

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import com.jjbaksa.domain.resp.user.WithdrawalReasonReq
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityWithdrawalBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.ui.login.LoginActivity
import com.jjbaksa.jjbaksa.ui.withdrawal.viewmodel.WithdrawalViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalActivity : BaseActivity<ActivityWithdrawalBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_withdrawal
    private val viewModel: WithdrawalViewModel by viewModels()

    override fun initView() {
        KeyboardProvider(this).inputKeyboardResize(window, binding.root)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        viewModel.getNickname()
    }

    override fun subscribe() {
        observeData()
    }

    private fun observeData() {
        viewModel.saveWithdrawalReasonState.observe(this) {
            if (it.isSuccess) {
                viewModel.withdraw()
            } else {
                showSnackBar(it.message.toString(), getString(R.string.close))
            }
        }
        viewModel.isWithdrawUser.observe(this) {
            if (it) {
                showCompleteWithdrawalDialog()
            }
        }
    }

    override fun initEvent() {
        binding.jjAppBarContainer.setOnClickListener { finish() }
        withdrawal()
        setWithdrawalReason()
        setFocusInputField()
        setInputField()
    }

    private fun withdrawal() {
        binding.withdrawalButton.setOnClickListener {
            if (viewModel.reason.value?.isNotEmpty()!! && binding.inputEditTextField.text?.isNotEmpty()!!) {
                val withdrawalReasonReq = WithdrawalReasonReq(
                    viewModel.reason.value.toString(),
                    binding.inputEditTextField.text.toString()
                )
                viewModel.saveWithdrawalReason(withdrawalReasonReq)
            }
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
            {
                ActivityCompat.finishAffinity(this)
                startActivity(Intent(this, LoginActivity::class.java))
            },
            "#222222"
        ).show(supportFragmentManager, WITHDRAWAL_DIALOG_TAG)
    }

    companion object {
        const val WITHDRAWAL_DIALOG_TAG = "WITHDRAWAL_DIALOG_TAG"
    }
}
