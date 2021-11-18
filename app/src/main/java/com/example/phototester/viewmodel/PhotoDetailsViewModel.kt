package com.example.phototester.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.phototester.dataprovider.MainInteractorInterface

class PhotoDetailsViewModel(private val interactor: MainInteractorInterface): ViewModel() {

    private val _mutableLiveData = MutableLiveData<Boolean>()
    val mutableLiveData: LiveData<Boolean>
        get() = _mutableLiveData

    fun onDeletePictureButtonPushed(name: String) {
        interactor.deletePhoto(name)
        _mutableLiveData.postValue(false)
    }

}