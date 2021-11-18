package com.example.phototester.dataprovider

import android.graphics.Bitmap
import kotlinx.coroutines.Deferred
import java.io.File

interface FileManagerInterface {
    suspend fun saveNewImageFile(image: Bitmap): File
    fun convertAndSaveImageAsync(image: Bitmap): Deferred<File>
    fun filterImageFiles(): Array<out File>?
    fun deleteAllImageFiles(): Array<out File>?
    fun deleteImageFile(name: String)
    fun setImageName (pictureDate: String): String
}