package com.example.phototester.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phototester.dataprovider.MainInteractorInterface
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.File

class GalleryViewModel(private val interactor: MainInteractorInterface): ViewModel() {

    private val _mutableLiveData = MutableLiveData<Array<out File>>()
    val mutableLiveData: LiveData<Array<out File>>
        get() = _mutableLiveData

    fun setGallery(){
        val filteredList = interactor.filterPhotos()
        _mutableLiveData.postValue(filteredList!!)
    }

    fun deleteAllData() {
        val emptyList = interactor.deleteAllPhotos()
        _mutableLiveData.postValue(emptyList!!)
    }

}