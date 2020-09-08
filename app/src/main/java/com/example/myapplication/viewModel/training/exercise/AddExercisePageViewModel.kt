package com.example.myapplication.viewModel.training.exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.ExercisesDao
import com.example.myapplication.database.model.WorkoutExerciseName
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddExercisePageViewModel : BaseViewModel() {

    @Inject
    lateinit var exercisesDao: ExercisesDao

    val onInsert = MutableLiveData<Any>()

    init {
        Injector.component.inject(this)
    }

    fun insertExercise(name: String) = viewModelScope.launch {
        exercisesDao.insert(WorkoutExerciseName(exerciseName = name))
        onInsert.postValue(true)
    }
}
