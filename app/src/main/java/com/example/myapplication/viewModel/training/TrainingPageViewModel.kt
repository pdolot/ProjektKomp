package com.example.myapplication.viewModel.training

import com.example.myapplication.R
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.model.MenuItem

class TrainingPageViewModel : BaseViewModel() {
    val menuItems = listOf(
        MenuItem("Ćwiczenia", R.id.page_exercises, icon = R.drawable.ic_fitness),
        MenuItem("Ustal trening", R.id.page_new_training, icon = R.drawable.ic_new_training),
        MenuItem("Zacznij trening", R.id.page_trainings, icon = R.drawable.ic_training)
    )
}