package com.jjbaksa.jjbaksa.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    open var ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
        _toast.value = throwable.message
    }

    private val _toast = MutableLiveData<String>()
    val toast: LiveData<String> get() = _toast
}
