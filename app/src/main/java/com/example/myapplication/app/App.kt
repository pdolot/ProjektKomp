package com.example.myapplication.app

import android.app.Application
import com.example.myapplication.di.Injector

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}