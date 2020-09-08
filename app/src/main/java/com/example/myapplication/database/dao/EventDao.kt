package com.example.myapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.model.EventDay

@Dao
interface EventDao {
    @Insert
    suspend fun insert(eventDay: EventDay)

    @Query("DELETE FROM eventday WHERE id=:id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM eventday WHERE date =:date")
    suspend fun getEventsByDate(date: Long): List<EventDay>

    @Query("SELECT * FROM eventday WHERE month =:month AND year = :year")
    suspend fun getEventsByMoth(month: Int, year: Int): List<EventDay>
}