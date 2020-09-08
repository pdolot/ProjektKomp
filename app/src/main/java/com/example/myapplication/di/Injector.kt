package com.example.myapplication.di

import com.example.myapplication.app.App
import com.example.myapplication.di.module.AppModule
import com.example.myapplication.di.module.DbModule

object Injector {

    lateinit var component: AppComponent

    fun init(app: App) {
        component = DaggerAppComponent.builder()
            .dbModule(DbModule(app))
            .appModule(AppModule(app))
            .build()
    }
}