package com.example.myapplication.viewModel.organizer.pomodoro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.SharedPrefLocalStorage
import com.example.myapplication.database.dao.PomodoroDao
import com.example.myapplication.database.model.Pomodoro
import com.example.myapplication.database.model.PomodoroStatus
import com.example.myapplication.di.Injector
import com.example.myapplication.util.TimeUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PomodoroActiveViewModel : BaseViewModel() {

    @Inject
    lateinit var pomodoroDao: PomodoroDao

    @Inject
    lateinit var localStorage: SharedPrefLocalStorage

    var currentSession: Pomodoro? = null

    val timer = MutableLiveData<Pair<String, Boolean>>()
    var isSessionStopped = false

    init {
        Injector.component.inject(this)
    }

    fun setSessionFailedIfNeeded() = viewModelScope.launch {
        currentSession?.let {
            pomodoroDao.setStatusFailed(it.id)
        }
    }

    fun stopSession() = viewModelScope.launch {
        isSessionStopped = true
        currentSession?.let {
            pomodoroDao.stopSession(it.id, ((System.currentTimeMillis() - it.startTime) / 1000))
        }
    }

    fun stopBreak() = viewModelScope.launch {
        isSessionStopped = true
        currentSession?.let {
            pomodoroDao.stopBreak(it.id, ((System.currentTimeMillis() - it.breakStartTime) / 1000))
        }
    }

    fun continueSession() = viewModelScope.launch {
        isSessionStopped = false
        currentSession?.let {
            pomodoroDao.continueSession(it.id, System.currentTimeMillis())
        }
    }

    fun continueBreak() = viewModelScope.launch {
        isSessionStopped = false
        currentSession?.let {
            pomodoroDao.continueBreak(it.id, System.currentTimeMillis())
        }
    }

    fun deleteSession() = viewModelScope.launch {
        pomodoroDao.delete()
    }

    fun increaseSession() = viewModelScope.launch {
        currentSession?.let {
            if (it.sessionCount == it.reachedSession + 1){
                pomodoroDao.increaseSession(it.id, System.currentTimeMillis(), PomodoroStatus.FINISHED.ordinal)
                localStorage.incrementPassedPomodoroSession()
            }else{
                pomodoroDao.increaseSession(it.id, System.currentTimeMillis())
            }

        }
    }

    fun startTimer() {
        currentSession?.let { session ->
            rxDisposer.add(
                Observable
                    .interval(1, TimeUnit.SECONDS)
                    .flatMap {
                        return@flatMap Observable.create<Pair<String, Boolean>> {
                            it.onNext(TimeUtil.getTime(session))
                            it.onComplete()
                        }
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        timer.postValue(it)
                    })
        }
    }
}
