package com.example.myapplication.util

import com.example.myapplication.database.model.Pomodoro
import com.example.myapplication.database.model.PomodoroStatus

object TimeUtil {

    fun getTime(pomodoro: Pomodoro): Pair<String, Boolean> {

        val startTimeDiff = when(pomodoro.status){
            PomodoroStatus.ACTIVE.ordinal -> {
                (System.currentTimeMillis() - pomodoro.startTime) / 1000
            }
            PomodoroStatus.BREAK.ordinal -> {
                (System.currentTimeMillis() - pomodoro.breakStartTime) / 1000
            }
            else -> 0L
        }
        var timeDiff = 0L
        var isSessionEnd = false

        when(pomodoro.status){
            PomodoroStatus.ACTIVE.ordinal -> {
                timeDiff = (pomodoro.sessionLength - pomodoro.reachedTime - startTimeDiff)
                isSessionEnd = timeDiff == 0L
            }
            PomodoroStatus.BREAK.ordinal -> {
                timeDiff = (pomodoro.breakLength - pomodoro.breakReachedTime - startTimeDiff)
                isSessionEnd = timeDiff == 0L
            }
        }

        val minutes = timeDiff / 60
        val seconds = timeDiff % 60
        val m = if (minutes < 10) "0$minutes" else "$minutes"
        val s = if (seconds < 10) "0$seconds" else "$seconds"

        return Pair("$m:$s", isSessionEnd)
    }

    fun justGetTime(pomodoro: Pomodoro): String {

        var timeDiff = 0L

        when(pomodoro.status){
            PomodoroStatus.STOPPED.ordinal-> {
                timeDiff = (pomodoro.sessionLength - pomodoro.reachedTime )
            }
            PomodoroStatus.BREAK_STOPPED.ordinal -> {
                timeDiff = (pomodoro.breakLength - pomodoro.breakReachedTime)
            }
        }

        val minutes = timeDiff / 60
        val seconds = timeDiff % 60
        val m = if (minutes < 10) "0$minutes" else "$minutes"
        val s = if (seconds < 10) "0$seconds" else "$seconds"

        return "$m:$s"
    }
}