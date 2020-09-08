package com.example.myapplication.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pomodoro(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionCount: Int,
    val sessionLength: Long,
    val breakLength: Long,
    var startTime: Long,
    var status: Int,
    var reachedSession: Int = 0,
    var reachedTime: Long = 0,
    var breakStartTime: Long = 0,
    var breakReachedTime: Long  = 0
)

enum class PomodoroStatus{
    ACTIVE,
    STOPPED,
    FAILED,
    BREAK,
    BREAK_STOPPED,
    FINISHED
}