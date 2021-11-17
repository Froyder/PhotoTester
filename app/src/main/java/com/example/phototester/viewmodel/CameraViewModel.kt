package com.example.phototester.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phototester.dataprovider.MainInteractorInterface
import kotlinx.coroutines.*
import timber.log.Timber

class CameraViewModel (private val interactor: MainInteractorInterface): ViewModel() {

    private val _mutableLiveData = MutableLiveData<Uri>()
    val mutableLiveData: LiveData<Uri>
        get() = _mutableLiveData

    private val viewModelScope = CoroutineScope(
        Dispatchers.IO
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    private var job: Job? = null

    fun onSaveButtonPushed(bitmap: Bitmap) {
        job?.cancel()
        job = viewModelScope.launch {
            _mutableLiveData.postValue(interactor.savePhoto(bitmap))
        }
    }

    private fun handleError(error: Throwable) {
        Timber.i("Timber talks: An error occurred: $error")
    }

    override fun onCleared() {
        job?.cancel()
        viewModelScope.cancel()
        super.onCleared()
    }

}