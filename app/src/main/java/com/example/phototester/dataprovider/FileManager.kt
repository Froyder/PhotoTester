package com.example.phototester.dataprovider

import android.content.Context
import android.graphics.Bitmap
import com.example.phototester.R
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

class FileManager(private val context: Context) : FileManagerInterface {

    private lateinit var stream: OutputStream
    private val path: String = context.filesDir?.absolutePath.toString()

    private val coroutinesScope = CoroutineScope(Dispatchers.IO)

    override suspend fun saveNewImageFile(image: Bitmap): File {
        return convertAndSaveImageAsync(image).await()
    }

    override fun convertAndSaveImageAsync(image: Bitmap): Deferred<File> =
        coroutinesScope.async {

            val sdf = SimpleDateFormat(context.getString(R.string.date_format), Locale.getDefault())
            val pictureDate = sdf.format(Date())

            val pictureName = setImageName(pictureDate)

            val newImage = File(path, pictureName)
            kotlin.runCatching {
                stream = FileOutputStream(newImage)
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            }
            newImage
        }

    override fun filterImageFiles(): Array<out File>? {
        val appDir = File(path)
        val filteredList = appDir.listFiles { file ->
            file.path.endsWith(context.getString(R.string.file_filter_suffix))
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

    override fun deleteImageFile(name: String) {
        val file = File(path, name)
        file.delete()
    }

    override fun setImageName(pictureDate: String): String {
        return context.getString(R.string.app_name) + pictureDate + context.getString(R.string.file_filter_suffix)
    }
}