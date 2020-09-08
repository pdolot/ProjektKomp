package com.example.myapplication.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutExerciseName(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val exerciseName: String
)