package com.jjbaksa.jjbaksa.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    open var ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
    }
}
