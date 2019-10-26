package com.ganesh.twitterapp.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {


    var disposable: CompositeDisposable = CompositeDisposable()


    // to show progress view
    var canShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    // to store error message
    var errorMessage: MutableLiveData<String> = MutableLiveData()


    override
    fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}