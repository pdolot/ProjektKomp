package com.example.myapplication.viewModel.organizer.pomodoro

import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.PomodoroDao
import com.example.myapplication.database.model.Pomodoro
import com.example.myapplication.database.model.PomodoroStatus
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class PomodoroPageViewModel : BaseViewModel() {

    @Inject
    lateinit var pomodoroDao: PomodoroDao

    init {
        Injector.component.inject(this)
    }

    fun insert(sessionCount: Int, sessionLength: Long, breakLength: Long) = viewModelScope.launch {
        val p = Pomodoro(
            sessionCount = sessionCount,
            sessionLength = sessionLength,
            breakLength = breakLength,
            status = PomodoroStatus.ACTIVE.ordinal,
            startTime = System.currentTimeMillis()
        )

        pomodoroDao.insert(p)
    }

}