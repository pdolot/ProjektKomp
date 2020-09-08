package com.example.myapplication.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    var rxDisposer = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        rxDisposer.dispose()
    }
}