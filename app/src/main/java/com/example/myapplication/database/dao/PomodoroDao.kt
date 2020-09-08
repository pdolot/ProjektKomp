package com.example.myapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.model.Pomodoro
import com.example.myapplication.database.model.PomodoroStatus

@Dao
interface PomodoroDao {

    @Insert
    suspend fun insert(pomodoro: Pomodoro)

    @Query("DELETE FROM pomodoro")
    suspend fun delete()

    @Query("SELECT * FROM pomodoro")
    fun getSession(): LiveData<List<Pomodoro>>

    @Query("SELECT * FROM pomodoro")
    suspend fun isAnySession(): List<Pomodoro>

    @Query("UPDATE pomodoro SET reachedTime = 0, breakReachedTime = 0, startTime = 0, reachedSession = reachedSession + 1, status = :status, breakStartTime =:time WHERE id =:id")
    suspend fun increaseSession(
        id: Long,
        time: Long,
        status: Int = PomodoroStatus.BREAK.ordinal
    )

    @Query("UPDATE pomodoro SET reachedTime = reachedTime + :time, status =:status WHERE id =:id")
    suspend fun stopSession(id: Long, time: Long, status: Int = PomodoroStatus.STOPPED.ordinal)

    @Query("UPDATE pomodoro SET breakReachedTime = breakReachedTime + :time, status =:status WHERE id =:id")
    suspend fun stopBreak(id: Long, time: Long, status: Int = PomodoroStatus.BREAK_STOPPED.ordinal)

    @Query("UPDATE pomodoro SET breakStartTime =:time, status =:status, startTime = 0, reachedTime = 0 WHERE id =:id")
    suspend fun continueBreak(id: Long, time: Long, status: Int = PomodoroStatus.BREAK.ordinal)

    @Query("UPDATE pomodoro SET status =:status, startTime =:time,  breakStartTime = 0, breakReachedTime = 0 WHERE id =:id")
    suspend fun continueSession(id: Long, time: Long, status: Int = PomodoroStatus.ACTIVE.ordinal)

    @Query("UPDATE pomodoro SET status =:status WHERE id =:id")
    suspend fun setStatusFailed(id: Long, status: Int = PomodoroStatus.FAILED.ordinal)
}