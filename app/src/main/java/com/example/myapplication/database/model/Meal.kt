package com.example.myapplication.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Meal(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val energy: Int,
    val proteins: Int,
    val fats: Int,
    val carbohydrates: Int,
    var date: Long?
)