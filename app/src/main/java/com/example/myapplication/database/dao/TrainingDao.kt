package com.example.myapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.model.WorkoutExercises

@Dao
interface TrainingDao {
    @Insert
    suspend fun insert(workoutExercises: WorkoutExercises): Long

    @Query("SELECT * FROM workoutexercises")
    fun getAll(): LiveData<List<WorkoutExercises>>

    @Query("SELECT COUNT(*) FROM workoutexercises")
    fun getTrainingCount(): LiveData<Int>

    @Query("DELETE FROM workoutexercises WHERE id= :id")
    suspend fun deleteById(id: Long)
}