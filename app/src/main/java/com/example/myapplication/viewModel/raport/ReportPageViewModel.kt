package com.example.myapplication.viewModel.raport

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.SharedPrefLocalStorage
import com.example.myapplication.database.dao.TrainingDao
import com.example.myapplication.database.dao.TrainingExerciseDao
import com.example.myapplication.di.Injector
import javax.inject.Inject

class ReportPageViewModel : BaseViewModel() {

    @Inject
    lateinit var localStorage: SharedPrefLocalStorage

    @Inject
    lateinit var trainingDao: TrainingDao

    @Inject
    lateinit var trainingExerciseDao: TrainingExerciseDao

    var trainingCount = MutableLiveData<Int>()
    var doneTrainingCount = MutableLiveData<Int>()

    init {
        Injector.component.inject(this)
        trainingCount.postValue(0)
        doneTrainingCount.postValue(0)
    }
}
