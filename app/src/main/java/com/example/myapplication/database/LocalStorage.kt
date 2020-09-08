package com.example.myapplication.database

interface LocalStorage {
    fun setCaloriesOfDay(value: Int)
    fun getCaloriesOfDay(): Int

    fun setProteinsOfDay(value: Int)
    fun getProteinsOfDay(): Int

    fun setFatsOfDay(value: Int)
    fun getFatsOfDay(): Int

    fun setCarbohydratesOfDay(value: Int)
    fun getCarbohydratesOfDay(): Int

    fun setNewBodyWeight(value: Float)
    fun getCurrentBodyWeight(): Float
    fun getPreviousBodyWeight(): Float

    fun getLastBodyWeightChange(): Long

    fun incrementPassedPomodoroSession()
    fun incrementFailedPomodoroSession()

    fun getPassedPomodoroSession(): Int
    fun getFailedPomodoroSession(): Int
}