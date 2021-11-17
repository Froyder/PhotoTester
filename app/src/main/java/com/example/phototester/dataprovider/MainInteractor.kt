package com.example.phototester.dataprovider

import android.graphics.Bitmap
import android.net.Uri
import java.io.File

class MainInteractor(private val fileManager: FileManagerInterface) : MainInteractorInterface {

    override suspend fun savePhoto(image: Bitmap): Uri {
        return Uri.parse(fileManager.saveNewImageFile(image).absolutePath)
    }

    override fun deleteAllPhotos(): Array<out File>? {
        return fileManager.deleteAllImageFiles()
    }

    override fun filterPhotos(): Array<out File>? {
        return fileManager.filterImageFiles()
    }

}