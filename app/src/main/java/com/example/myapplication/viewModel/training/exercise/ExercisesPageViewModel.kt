package com.example.myapplication.viewModel.training.exercise

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.ExercisesDao
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExercisesPageViewModel : BaseViewModel() {

    @Inject
    lateinit var exercisesDao: ExercisesDao

    init {
        Injector.component.inject(this)
    }

    fun delete(it: Long) = viewModelScope.launch {
        exercisesDao.deleteById(it)
    }
}

