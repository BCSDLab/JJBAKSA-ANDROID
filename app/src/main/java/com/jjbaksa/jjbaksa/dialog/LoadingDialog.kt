package com.jjbaksa.jjbaksa.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.base.BaseDialogFragment
import com.jjbaksa.jjbaksa.databinding.DialogLoadingBinding

class LoadingDialog: BaseDialogFragment<DialogLoadingBinding>() {
    override val layoutResId: Int
        get() = R.layout.dialog_loading

    override fun initView(view: View) {
        isCancelable = false
    }

    override fun initEvent() {
    }

    override fun subscribe() {
    }

    override fun initData() {
    }
    companion object {
        val TAG = this.javaClass.simpleName
    }
}