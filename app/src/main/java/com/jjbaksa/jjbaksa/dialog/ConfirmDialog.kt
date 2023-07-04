package com.jjbaksa.jjbaksa.dialog

import android.app.Dialog
import android.view.View
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseDialogFragment
import com.jjbaksa.jjbaksa.databinding.DialogConfirmBinding

class ConfirmDialog(
    val title: String,
    val msg: String? = null,
    val confirmText: String,
    val confirmClick: (Dialog) -> Unit,
    val isCancel: Boolean = true
): BaseDialogFragment<DialogConfirmBinding>() {
    override val layoutResId: Int
        get() = R.layout.dialog_confirm

    override fun initView(view: View) {
        isCancelable = isCancel
        with(binding) {
            confirmDialogTitle.text = title
            if (msg == null) {
                confirmDialogMsg.visibility = View.GONE
            } else {
                confirmDialogMsg.visibility = View.VISIBLE
                confirmDialogMsg.text = msg
            }
            confirmDialogButton.text = confirmText

        }
    }

    override fun initEvent() {
        binding.confirmDialogButton.setOnClickListener {
            dialog?.let { confirmClick(it) }
        }
    }

    override fun subscribe() {
    }

    override fun initData() {
    }
    companion object {
        val TAG = this.javaClass.simpleName
    }
}