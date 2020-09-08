package com.example.myapplication.viewModel

import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.PomodoroDao
import com.example.myapplication.di.Injector
import com.example.myapplication.model.MenuItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel : BaseViewModel(){

    @Inject
    lateinit var dao: PomodoroDao

    init {
        Injector.component.inject(this)
    }

    fun deleteSession() = viewModelScope.launch {
        dao.delete()
    }

    val menuItems = listOf(
        MenuItem("Posiłki", R.id.page_meals),
        MenuItem("Organizer", R.id.page_organizer),
        MenuItem("Trening", R.id.page_training),
        MenuItem("Raport", R.id.page_report)
    )
}