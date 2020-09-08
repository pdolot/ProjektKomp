package com.example.myapplication.database.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class WorkoutExercises(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long = System.currentTimeMillis()
){
    @Ignore
    var doneExercises: Int = 0
    @Ignore
    var isDone: Boolean = false
}