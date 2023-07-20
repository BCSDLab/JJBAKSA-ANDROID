package com.example.imageselector.repository

import android.content.ContentResolver
import android.provider.MediaStore
import android.util.Log
import com.example.imageselector.model.Image
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val contentResolver: ContentResolver,
) : ImageRepository {

    private val selectedImages = ArrayList<Image>()
    private val selectedImageUri = ArrayList<String>()
    private val uriArr = ArrayList<String>()

    override fun getAllPhotos() {
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriArr.add(uri)
            }
            cursor.close()
        }
        for (uri in uriArr) {
            selectedImages.add(Image(uri, 0, false))
        }
    }

    override fun selectImage(imageUri: String) {
        if (!selectedImageUri.contains(imageUri)) {
            selectedImageUri.add(imageUri)
        } else {
            selectedImageUri.remove(imageUri)
        }
        refreshSelectList()
    }

    override fun refreshSelectList() {
        for (data in selectedImages) {
            data.index = 0
            data.isSelected = false
        }

        for (i in 0 until selectedImageUri.size) {
            val path = selectedImageUri[i]
            for (data in selectedImages) {
                if (data.uri.equals(path)) {
                    data.index = i + 1
                    data.isSelected = true
                    continue
                }
            }
        }
    }

    override fun getSelectedImageUri(): ArrayList<String> {
        return selectedImageUri
    }

    override fun getUriArr(): ArrayList<String> {
        return uriArr
    }

    override fun getSelectedImages(): ArrayList<Image> {
        return selectedImages
    }
}
