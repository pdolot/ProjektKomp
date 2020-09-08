package com.example.myapplication.viewModel.training.trainings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.TrainingDao
import com.example.myapplication.database.dao.TrainingExerciseDao
import com.example.myapplication.database.model.WorkoutExercises
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class PageTrainingsViewModel : BaseViewModel() {

    @Inject
    lateinit var exerciseDao: TrainingDao

    @Inject
    lateinit var trainingExerciseDao: TrainingExerciseDao

    val trainings = MutableLiveData<List<WorkoutExercises>>()

    init {
        Injector.component.inject(this)
    }

    fun removeById(id: Long) = viewModelScope.launch {
        exerciseDao.deleteById(id)
        trainingExerciseDao.deleteByParentId(id)
    }
}