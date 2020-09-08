package com.example.myapplication.viewModel.home

import com.example.myapplication.R
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.SharedPrefLocalStorage
import com.example.myapplication.di.Injector
import com.example.myapplication.model.MenuItem
import org.joda.time.LocalDateTime
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {
    @Inject
    lateinit var localStorage: SharedPrefLocalStorage

    private val today = LocalDateTime.now()
    val todayInMilliseconds = LocalDateTime(today.year, today.monthOfYear, today.dayOfMonth, 0, 0).toDate().time

    init {
        Injector.component.inject(this)
    }

    val menuItems = listOf(
        MenuItem("POSIŁKI", R.id.page_meals, "https://image.freepik.com/free-photo/plate-with-paleo-diet-food-boiled-eggs-avocado-cucumber-nuts-cherry-strawberries-paleo-breakfast_2829-3703.jpg"),
        MenuItem("ORGANIZER", R.id.page_organizer,"https://image.freepik.com/free-photo/laptop-with-graphics-desk_1218-558.jpg"),
        MenuItem("TRENING", R.id.page_training,"https://image.freepik.com/free-photo/athletic-man-doing-exercise-with-crossfit-kettlebel_58466-11673.jpg"),
        MenuItem("RAPORT", R.id.page_report,"https://image.freepik.com/free-photo/businessman-analyzing-company-financial-report-balance-with-digital-augmented-reality-graphics_34141-379.jpg")
    )

    var currentItem = 0
}