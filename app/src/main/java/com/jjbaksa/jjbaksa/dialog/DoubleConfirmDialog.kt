package com.jjbaksa.jjbaksa.dialog

import android.app.Dialog
import android.view.View
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseDialogFragment
import com.jjbaksa.jjbaksa.databinding.DialogSignOutBinding

class DoubleConfirmDialog(
    val title: String,
    val msg: String,
    val confirmClick: (Dialog) -> Unit,
    val isCancel: Boolean = true
) : BaseDialogFragment<DialogSignOutBinding>() {
    override val layoutResId: Int
        get() = R.layout.dialog_sign_out

    override fun initView(view: View) {
        isCancelable = isCancel
        binding.signOutDialogTitle.text = title
        binding.signOutDialogMsg.text = msg
        binding.signOutCancelButton.text = getString(R.string.cancel)
        binding.signOutConfirmButton.text = getString(R.string.confirm)
    }

    override fun initEvent() {
        binding.signOutCancelButton.setOnClickListener {
            dismiss()
        }
        binding.signOutConfirmButton.setOnClickListener {
            dialog?.let {
                confirmClick(it)
            }
        }
    }

    override fun subscribe() {
    }

    override fun initData() {
    }
}
