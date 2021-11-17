package com.example.phototester.dataprovider

import android.content.Context
import android.graphics.Bitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class FileManager(context: Context) : FileManagerInterface {

    private lateinit var stream: OutputStream
    private val path: String = context.filesDir?.absolutePath.toString()

    private val coroutinesScope = CoroutineScope(Dispatchers.IO)

    override suspend fun saveNewImageFile(image: Bitmap): File {
        return convertAndSaveImageAsync(image).await()
    }

    override fun convertAndSaveImageAsync(image: Bitmap): Deferred<File> =
        coroutinesScope.async {

            val sdf = SimpleDateFormat("_dd-M-yyyy_hh:mm:ss", Locale.getDefault())
            val pictureDate = sdf.format(Date())

            val newImage = File(path, "PhotoTester$pictureDate.jpg")
            kotlin.runCatching {
                stream = FileOutputStream(newImage)
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }
            Timber.i("Timber ${newImage.absolutePath}")
            newImage
        }

    override fun filterImageFiles(): Array<out File>? {
        val appDir = File(path)
        val filteredList = appDir.listFiles { file ->
            file.path.endsWith(".jpg")
        }
        return filteredList
    }

    override fun deleteAllImageFiles(): Array<out File>? {
        val filesToDelete = File(path).list()
        for (element in filesToDelete) {
            val file = File(path, element)
            file.delete()
        }
        return File(path).listFiles()
    }
}