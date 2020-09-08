package com.example.myapplication.database

import android.content.Context
import org.joda.time.LocalDateTime

class SharedPrefLocalStorage(context: Context) : LocalStorage  {

    private val sharedPref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object{
        const val PREFS_NAME = "LOCAL_STORAGE_PREF"
        const val MAX_CALORIES = "MAX_CALORIES"
        const val MAX_PROTEINS = "MAX_PROTEINS"
        const val MAX_FATS = "MAX_FATS"
        const val MAX_CARBOHYDRATES = "MAX_CARBOHYDRATES"
        const val CURRENT_BODY_WEIGHT = "CURRENT_BODY_WEIGHT"
        const val PREVIOUS_BODY_WEIGHT = "PREVIOUS_BODY_WEIGHT"
        const val LAST_BODY_WEIGHT_CHANGE = "LAST_BODY_WEIGHT_CHANGE"
        const val PASSED_SESSION = "PASSED_SESSION"
        const val FAILED_SESSION = "FAILED_SESSION"
    }

    override fun setCaloriesOfDay(value: Int) {
        sharedPref.edit().apply {
            putInt(MAX_CALORIES, value)
            apply()
        }
    }

    override fun getCaloriesOfDay(): Int =
        sharedPref.getInt(MAX_CALORIES, 2000)

    override fun setProteinsOfDay(value: Int) {
        sharedPref.edit().apply {
            putInt(MAX_PROTEINS, value)
            apply()
        }
    }

    override fun getProteinsOfDay(): Int = sharedPref.getInt(MAX_PROTEINS, 75)


    override fun setFatsOfDay(value: Int) {
        sharedPref.edit().apply {
            putInt(MAX_FATS, value)
            apply()
        }
    }

    override fun getFatsOfDay(): Int = sharedPref.getInt(MAX_FATS, 150)

    override fun setCarbohydratesOfDay(value: Int) {
        sharedPref.edit().apply {
            putInt(MAX_CARBOHYDRATES, value)
            apply()
        }
    }

    override fun getCarbohydratesOfDay(): Int = sharedPref.getInt(MAX_CARBOHYDRATES, 150)

    override fun setNewBodyWeight(value: Float) {
        val prev = getCurrentBodyWeight()
        val today = LocalDateTime.now()
        val todayInMilliseconds = LocalDateTime(today.year, today.monthOfYear, today.dayOfMonth, 0, 0).toDate().time


        sharedPref.edit().apply {
            putFloat(CURRENT_BODY_WEIGHT, value)
            putFloat(PREVIOUS_BODY_WEIGHT, prev)
            putLong(LAST_BODY_WEIGHT_CHANGE, todayInMilliseconds)
            apply()
        }
    }

    override fun getCurrentBodyWeight(): Float = sharedPref.getFloat(CURRENT_BODY_WEIGHT, -1f)

    override fun getPreviousBodyWeight(): Float =  sharedPref.getFloat(PREVIOUS_BODY_WEIGHT, -1f)

    override fun getLastBodyWeightChange(): Long = sharedPref.getLong(LAST_BODY_WEIGHT_CHANGE, 0L)

    override fun incrementPassedPomodoroSession() {
        val next = getPassedPomodoroSession() + 1
        sharedPref.edit().apply {
            putInt(PASSED_SESSION, next)
            apply()
        }
    }

    override fun incrementFailedPomodoroSession() {
        val next = getFailedPomodoroSession() + 1
        sharedPref.edit().apply {
            putInt(FAILED_SESSION, next)
            apply()
        }
    }

    override fun getPassedPomodoroSession(): Int = sharedPref.getInt(PASSED_SESSION, 0)
    override fun getFailedPomodoroSession(): Int =  sharedPref.getInt(FAILED_SESSION, 0)

}