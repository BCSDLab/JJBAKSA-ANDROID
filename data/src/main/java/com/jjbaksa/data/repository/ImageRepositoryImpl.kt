package com.jjbaksa.data.repository

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jjbaksa.domain.model.Image
import com.jjbaksa.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
) : ImageRepository {

    private val selectedImage = ArrayList<Image>()
    private val selectedImageUri = ArrayList<String>()

    val currentValue: LiveData<Int>
        get() = _currentValue
    private val _currentValue = MutableLiveData<Int>()

    private val uriArr = ArrayList<String>()

    override fun getAllPhotos() {
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC")
        val imageList = ArrayList<Image>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriArr.add(uri)
            }
            cursor.close()
        }
        for (uri in uriArr) {
            selectedImage.add(Image(uri, 0, false))
            imageList.add(Image(uri, 0, false))
        }
    }

    override fun selectImage(imageUri: String) {
        if (!selectedImageUri.contains(imageUri)) {
            if (getSelectedImageUri().size + 1 <= 10) {
                selectedImageUri.add(imageUri)
            }
        } else {
            selectedImageUri.remove(imageUri)
        }
        refreshSelectList()
    }

    override fun refreshSelectList() {
        for (data in selectedImage) {
            data.index = 0
            data.isSelected = false
        }

        for (i in 0 until selectedImageUri.size) {
            val path = selectedImageUri[i]
            if (getSelectedImageUri().size <= 10) {
                for (data in selectedImage) {
                    if (data.uri.equals(path)) {
                        data.index = i + 1
                        data.isSelected = true
                        continue
                    }
                }
            }
        }
        _currentValue.value = selectedImageUri.size
    }

    override fun getSelectedImageUri(): ArrayList<String> {
        return selectedImageUri
    }

    override fun getUriArr(): ArrayList<String> {
        return uriArr
    }

    override fun getSelectedImageList(): ArrayList<Image> {
        return selectedImage
    }
}