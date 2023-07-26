package com.example.imageselector.gallery

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imageselector.model.Image
import com.example.imageselector.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: ImageRepository,
) : ViewModel() {
    private val _currentValue = MutableLiveData<Int>()
    val currentValue: LiveData<Int> get() = _currentValue

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

    fun getSelectedImages(): ArrayList<Image> {
        return repository.getSelectedImages()
    }

    fun clearData() {
        with(repository) {
            getUriArr().clear()
            getSelectedImages().clear()
            getSelectedImageUri().clear()
        }
    }
}


