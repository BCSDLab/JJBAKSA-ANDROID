package com.jjbaksa.domain.repository

import com.jjbaksa.domain.model.Image

interface ImageRepository {
    fun getAllPhotos()
    fun selectImage(imageUri: String)
    fun refreshSelectList()
    fun getSelectedImageUri(): ArrayList<String>
    fun getUriArr(): ArrayList<String>
    fun getSelectedImageList(): ArrayList<Image>
}