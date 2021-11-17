package com.example.phototester.dataprovider

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

interface MainInteractorInterface {

    suspend fun savePhoto(image: Bitmap): Uri
    fun deleteAllPhotos(): Array<out File>?
    fun filterPhotos(): Array<out File>?
}