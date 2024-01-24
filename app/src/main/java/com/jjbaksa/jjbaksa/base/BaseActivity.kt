package com.jjbaksa.jjbaksa.base

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.jjbaksa.jjbaksa.R
import com.jjbaksa.jjbaksa.dialog.LoadingDialog

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutId: Int
    private lateinit var _binding: T
    val binding: T
        get() = _binding
    private var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        initState()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    abstract fun initView()
    abstract fun subscribe()
    abstract fun initEvent()
    open fun initState() {
        initView()
        initEvent()
        subscribe()
    }

    fun isPermissionGranted(perm: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED
    }

    fun showLoading() {
        loadingDialog = LoadingDialog()
        loadingDialog?.show(supportFragmentManager, LoadingDialog.TAG)
    }

    fun dismissLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    fun showSnackBar(
        msg: String,
        actionMsg: String = getString(R.string.close),
        doAction: () -> Unit = { }
    ) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).also {
            it.setAction(
                actionMsg,
                object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        if (actionMsg != getString(R.string.close)) {
                            doAction()
                        }
                        it.dismiss()
                    }
                }
            )
        }.apply {
            view.setBackgroundColor(ContextCompat.getColor(context, R.color.color_80222222))
            view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                setActionTextColor(ContextCompat.getColor(context, R.color.color_ff7f23))
            }
        }.show()
    }
}
