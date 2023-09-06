package com.jjbaksa.jjbaksa.ui.withdrawal

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import com.jjbaksa.domain.model.user.WithdrawalReasonReq
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseActivity
import com.jjbaksa.jjbaksa.databinding.ActivityWithdrawalBinding
import com.jjbaksa.jjbaksa.dialog.ConfirmDialog
import com.jjbaksa.jjbaksa.ui.login.LoginActivity
import com.jjbaksa.jjbaksa.ui.withdrawal.viewmodel.WithdrawalViewModel
import com.jjbaksa.jjbaksa.util.KeyboardProvider
import com.jjbaksa.jjbaksa.util.MyInfo
import com.jjbaksa.jjbaksa.util.setTextProperties
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
        binding.withdrawalTitleTextView.text =
            setTextProperties(
                MyInfo.nickname + getString(R.string.withdrawal_jjbaksa_title),
                MyInfo.nickname.length
            )
    }

    override fun subscribe() {
        viewModel.toastMsg.observe(this) {
            showSnackBar(it, getString(R.string.close))
        }
        viewModel.withdrawalReasonResult.observe(this) {
            if (it) {
                viewModel.withdraw()
            }
        }
        viewModel.withdrawalResult.observe(this) {
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
            if (viewModel.reason.value.isNullOrEmpty()) {
                showSnackBar("계정을 삭제하려는 이유를 선택해주세요.", getString(R.string.close))
                KeyboardProvider(this).hideKeyboard(binding.root)
            } else if (binding.contentEditText.text.isNullOrEmpty()) {
                showSnackBar("개선해야 될 사항을 적어주세요.", getString(R.string.close))
                KeyboardProvider(this).hideKeyboard(binding.contentEditText)
            } else {
                val withdrawalReasonReq = WithdrawalReasonReq(
                    viewModel.reason.value.toString(),
                    binding.contentEditText.text.toString()
                )
                viewModel.postUserWithdrawalReason(withdrawalReasonReq)
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
        binding.contentEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (binding.contentEditText.text.toString() == getString(R.string.free_write)) {
                    binding.contentEditText.text?.clear()
                }
            }
        }
    }

    private fun setInputField() {
        binding.contentEditText.addTextChangedListener {
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
