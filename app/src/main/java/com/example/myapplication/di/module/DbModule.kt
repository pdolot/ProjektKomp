package com.example.myapplication.di.module

import androidx.room.Room
import com.example.myapplication.app.App
import com.example.myapplication.database.LocalDatabase
import com.example.myapplication.database.dao.*
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val application: App) {

    @Provides
    fun provideRoomDatabase(): LocalDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            LocalDatabase::class.java,
            "LocalDb"
        ).build()
    }


    @Provides
    fun provideMealsDao(database: LocalDatabase): MealsDao {
        return database.mealsDao()
    }

    @Provides
    fun provideMealsSetDao(database: LocalDatabase): MealsSetDao {
        return database.mealsSetDao()
    }

    @Provides
    fun provideEventDao(database: LocalDatabase): EventDao {
        return database.eventDao()
    }

    @Provides
    fun provideNoteDao(database: LocalDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    fun providePomodoroDao(database: LocalDatabase): PomodoroDao {
        return database.pomodoroDao()
    }

    @Provides
    fun provideExercisesDao(database: LocalDatabase): ExercisesDao {
        return database.exercisesDao()
    }

    @Provides
    fun provideTrainingDao(database: LocalDatabase): TrainingDao {
        return database.trainingDao()
    }

    @Provides
    fun provideTrainingExercisesDao(database: LocalDatabase): TrainingExerciseDao {
        return database.trainingExercisesDao()
    }

}