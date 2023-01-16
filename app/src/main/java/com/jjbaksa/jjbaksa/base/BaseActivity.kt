package com.jjbaksa.jjbaksa.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutId: Int
    private lateinit var _binding: T
    val binding: T
        get() = _binding
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
    fun shortToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    }
    fun longToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
    }
}
