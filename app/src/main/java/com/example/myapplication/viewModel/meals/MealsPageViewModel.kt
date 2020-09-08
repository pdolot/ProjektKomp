package com.example.myapplication.viewModel.meals

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.SharedPrefLocalStorage
import com.example.myapplication.database.dao.MealsDao
import com.example.myapplication.database.model.Meal
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.LocalDate
import javax.inject.Inject

class MealsPageViewModel() : BaseViewModel() {

    @Inject
    lateinit var dao: MealsDao

    @Inject
    lateinit var localStorage: SharedPrefLocalStorage

    private val today = LocalDate.now()
    val queryDate = DateTime(today.year,today.monthOfYear, today.dayOfMonth , 0 ,0)

    val meals = MutableLiveData<List<Meal>>()
    val calories = MutableLiveData<Int>()
    val fats = MutableLiveData<Int>()
    val proteins = MutableLiveData<Int>()
    val carbohydrates = MutableLiveData<Int>()

    init {
        Injector.component.inject(this)
        calories.postValue(0)
        fats.postValue(0)
        proteins.postValue(0)
        carbohydrates.postValue(0)
    }

    fun deleteMeal(id: Long) = viewModelScope.launch {
        dao.deleteMealById(id)
    }
}
