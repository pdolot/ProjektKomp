package com.example.myapplication.di

import com.example.myapplication.di.module.AppModule
import com.example.myapplication.di.module.DbModule
import com.example.myapplication.viewModel.MainActivityViewModel
import com.example.myapplication.viewModel.home.HomeViewModel
import com.example.myapplication.viewModel.meals.AddMealFromListViewModel
import com.example.myapplication.viewModel.meals.AddMealViewModel
import com.example.myapplication.viewModel.meals.MealsPageViewModel
import com.example.myapplication.viewModel.organizer.OrganizerPageViewModel
import com.example.myapplication.viewModel.organizer.calendar.CalendarPageViewModel
import com.example.myapplication.viewModel.organizer.note.AddNotePageViewModel
import com.example.myapplication.viewModel.organizer.note.NotePageViewModel
import com.example.myapplication.viewModel.organizer.pomodoro.PomodoroActiveViewModel
import com.example.myapplication.viewModel.organizer.pomodoro.PomodoroPageViewModel
import com.example.myapplication.viewModel.raport.ReportPageViewModel
import com.example.myapplication.viewModel.training.exercise.AddExercisePageViewModel
import com.example.myapplication.viewModel.training.exercise.ExercisesPageViewModel
import com.example.myapplication.viewModel.training.newTraining.AddExerciseToNewTrainingPageViewModel
import com.example.myapplication.viewModel.training.newTraining.PageNewTrainingViewModel
import com.example.myapplication.viewModel.training.trainings.PageTrainingsViewModel
import com.example.myapplication.viewModel.training.trainings.StartTrainingPageViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DbModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(into: MealsPageViewModel)
    fun inject(into: AddMealViewModel)
    fun inject(into: CalendarPageViewModel)
    fun inject(into: NotePageViewModel)
    fun inject(into: AddNotePageViewModel)
    fun inject(into: PomodoroPageViewModel)
    fun inject(into: PomodoroActiveViewModel)
    fun inject(into: OrganizerPageViewModel)
    fun inject(into: MainActivityViewModel)
    fun inject(into: ExercisesPageViewModel)
    fun inject(into: AddExercisePageViewModel)
    fun inject(into: PageNewTrainingViewModel)
    fun inject(into: AddExerciseToNewTrainingPageViewModel)
    fun inject(into: PageTrainingsViewModel)
    fun inject(into: StartTrainingPageViewModel)
    fun inject(into: AddMealFromListViewModel)
    fun inject(into: HomeViewModel)
    fun inject(into: ReportPageViewModel)
}