package com.jjbaksa.jjbaksa.ui.gallery

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jjbaksa.domain.model.Image
import com.jjbaksa.domain.repository.ImageRepository
import com.jjbaksa.jjbaksa.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: ImageRepository,
) : BaseViewModel() {
    val currentValue: LiveData<Int>
        get() = _currentValue
    private val _currentValue = MutableLiveData<Int>()

    fun getAllPhotos() {
        repository.getAllPhotos()
    }

    fun selectImage(imageUri: String) {
        repository.selectImage(imageUri)
        refreshSelectList()
    }

    private fun refreshSelectList() {
        _currentValue.value = repository.getSelectedImageUri().size
    }

    fun getSelectedImageUri(): ArrayList<String> {
        return repository.getSelectedImageUri()
    }

    fun getUriArr(): ArrayList<String> {
        return repository.getUriArr()
    }

    fun getSelectedImageList(): ArrayList<Image> {
        return repository.getSelectedImageList()
    }
}

object DataBindingAdapterUtil {
    @JvmStatic
    @BindingAdapter("select")
    fun select(view: View, b: Boolean) {
        view.isSelected = b
    }
}
