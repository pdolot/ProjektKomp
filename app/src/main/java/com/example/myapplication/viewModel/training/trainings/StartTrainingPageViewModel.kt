package com.example.myapplication.viewModel.training.trainings

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.TrainingExerciseDao
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartTrainingPageViewModel : BaseViewModel() {

    @Inject
    lateinit var exercisesDao: TrainingExerciseDao

    init {
        Injector.component.inject(this)
    }

    fun update(id: Long, status: Boolean) = viewModelScope.launch {
        exercisesDao.updateStatus(id, status)
    }
}