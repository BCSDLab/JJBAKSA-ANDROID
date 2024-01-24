package com.jjbaksa.jjbaksa.base

import androidx.lifecycle.ViewModel
import com.jjbaksa.jjbaksa.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {
    val isLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val toastMsg: SingleLiveEvent<String> = SingleLiveEvent()
    open var ceh = CoroutineExceptionHandler { coroutineContext, throwable ->
    }
}
