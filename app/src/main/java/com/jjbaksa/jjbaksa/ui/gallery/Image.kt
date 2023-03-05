package com.jjbaksa.jjbaksa.ui.gallery

import androidx.lifecycle.MutableLiveData

data class Image(
    val uri: MutableLiveData<String>,
    var index: MutableLiveData<Int>,
    var isSelected: Boolean
)
