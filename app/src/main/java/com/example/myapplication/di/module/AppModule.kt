package com.example.myapplication.di.module

import com.example.myapplication.app.App
import com.example.myapplication.database.SharedPrefLocalStorage
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    fun provideLocalStorage(): SharedPrefLocalStorage {
        return SharedPrefLocalStorage(app.applicationContext)
    }
}