package com.jjbaksa.jjbaksa.ui.gallery

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jjbaksa.jjbaksa.base.BaseViewModel

class GalleryViewModel() : BaseViewModel() {

    val selectedImage = ArrayList<Image>()

    val selectedImageUri = ArrayList<String>()

    val currentValue: LiveData<Int>
        get() = _currentValue
    private val _currentValue = MutableLiveData<Int>()

    fun selectImage(imageUri: String) {
        if (!selectedImageUri.contains(imageUri)) {
            selectedImageUri.add(imageUri)
        } else {
            selectedImageUri.remove(imageUri)
        }
        refreshSelectList()
    }

    private fun refreshSelectList() {
        for (data in selectedImage) {
            data.index.value = 0
            data.isSelected = false
        }

        for (i in 0 until selectedImageUri.size) {
            val path = selectedImageUri[i]
            for (data in selectedImage) {
                if (data.uri.value.equals(path)) {
                    data.index.postValue(i + 1)
                    data.isSelected = true
                    continue
                }
            }
        }
        _currentValue.value = selectedImageUri.size
    }
}

object DataBindingAdapterUtil {
    @JvmStatic
    @BindingAdapter("select")
    fun select(view: View, b: Boolean) {
        view.isSelected = b
    }
}