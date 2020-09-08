package com.example.myapplication.viewModel.meals

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.MealsDao
import com.example.myapplication.database.dao.MealsSetDao
import com.example.myapplication.database.model.Meal
import com.example.myapplication.database.model.MealSet
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

class AddMealViewModel : BaseViewModel() {

    @Inject
    lateinit var dao: MealsDao

    @Inject
    lateinit var mealsSetDao: MealsSetDao

    private val currentDate = DateTime.now()
    private val queryDate = DateTime(currentDate.year,currentDate.monthOfYear, currentDate.dayOfMonth , 0 ,0)
    val onInsert = MutableLiveData<Any>()

    init {
        Injector.component.inject(this)
    }

    fun insertMeal(
        name: String, energy: Int, proteins: Int, fats: Int, carbohydrates: Int
    ) = viewModelScope.launch {
        dao.insert(
            Meal(
                name = name,
                energy = energy,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates,
                date = queryDate.toDate().time
            )
        )

        mealsSetDao.insert(
            MealSet(
                name = name,
                energy = energy,
                proteins = proteins,
                fats = fats,
                carbohydrates = carbohydrates
            )
        )

        onInsert.postValue(true)

    }
}