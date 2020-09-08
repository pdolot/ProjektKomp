package com.example.myapplication.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WorkoutExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val parentId: Long,
    val exerciseName: String,
    val repeatCount: Int,
    val load: Float? = null,
    val time: Long? = null,
    val done: Boolean = false
)