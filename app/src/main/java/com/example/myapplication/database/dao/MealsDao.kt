package com.example.myapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.database.model.Meal

@Dao
interface MealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: Meal)

    @Query("SELECT * FROM meal WHERE date=:date")
    fun getMealsByDay(date: Long): LiveData<List<Meal>>

    @Query("DELETE FROM meal WHERE id=:id")
    suspend fun deleteMealById(id: Long)

    @Query("SELECT * FROM meal")
    fun getAllMeals(): LiveData<List<Meal>>
}