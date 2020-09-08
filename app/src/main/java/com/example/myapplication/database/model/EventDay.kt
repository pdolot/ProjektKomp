package com.example.myapplication.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class EventDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Long = Date().time,
    val name: String,
    val month: Int,
    val year: Int
)