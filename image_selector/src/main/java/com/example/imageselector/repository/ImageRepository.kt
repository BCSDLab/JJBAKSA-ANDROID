package com.example.imageselector.repository

import com.example.imageselector.model.Image

interface ImageRepository {
    fun getAllPhotos()
    fun selectImage(imageUri: String)
    fun refreshSelectList()
    fun getSelectedImageUri(): ArrayList<String>
    fun getUriArr(): ArrayList<String>
    fun getSelectedImages(): ArrayList<Image>
}
