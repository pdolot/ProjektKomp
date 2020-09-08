package com.example.myapplication.view.page.organizer.pomodoro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.database.model.Pomodoro
import com.example.myapplication.database.model.PomodoroStatus
import com.example.myapplication.util.TimeUtil
import com.example.myapplication.viewModel.organizer.pomodoro.PomodoroActiveViewModel
import kotlinx.android.synthetic.main.page_pomodoro_active.*

class PomodoroActive : BaseFragment() {
    private val viewModel by lazy { PomodoroActiveViewModel() }
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Aktywna sesja"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_pomodoro_active, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keepAlwaysScreenON.setOnCheckedChangeListener { _, isChecked ->
            view.keepScreenOn = isChecked
        }

        endSession.setOnClickListener {
            viewModel.deleteSession()
            findNavController().popBackStack()
        }


        stopSession.setOnClickListener {
            if (viewModel.currentSession?.status == PomodoroStatus.ACTIVE.ordinal) {
                viewModel.stopSession()
            } else if (viewModel.currentSession?.status == PomodoroStatus.STOPPED.ordinal) {
                viewModel.continueSession()
            }else if (viewModel.currentSession?.status == PomodoroStatus.BREAK.ordinal) {
                viewModel.stopBreak()
            }else if (viewModel.currentSession?.status == PomodoroStatus.BREAK_STOPPED.ordinal) {
                viewModel.continueBreak()
            }
        }

        viewModel.pomodoroDao.getSession().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                it[0].let {
                    viewModel.currentSession = it
                    if (it.status == PomodoroStatus.STOPPED.ordinal){
                        time.text = TimeUtil.justGetTime(it)
                        viewModel.isSessionStopped = true
                        viewModel.rxDisposer.clear()
                        sessionCountLabel.text = "Sesja ${it.reachedSession + 1}"
                    }else if (it.status == PomodoroStatus.BREAK_STOPPED.ordinal){
                        time.text = TimeUtil.justGetTime(it)
                        viewModel.isSessionStopped = true
                        viewModel.rxDisposer.clear()
                        sessionCountLabel.text = "PRZERWA | ${it.reachedSession}"
                    }else if (it.status == PomodoroStatus.ACTIVE.ordinal){
                        viewModel.timer.postValue(TimeUtil.getTime(it))
                        viewModel.isSessionStopped = false
                        viewModel.startTimer()
                        sessionCountLabel.text = "Sesja ${it.reachedSession + 1}"
                    }else if (it.status == PomodoroStatus.BREAK.ordinal){
                        viewModel.timer.postValue(TimeUtil.getTime(it))
                        viewModel.isSessionStopped = false
                        viewModel.startTimer()
                        sessionCountLabel.text = "PRZERWA | ${it.reachedSession}"
                    }else if(it.status == PomodoroStatus.FINISHED.ordinal){
                        sessionCountLabel.visibility = View.GONE
                        stopSession.visibility = View.GONE
                        time.text = "SESJA WYKONANA"
                        time.textSize = 24f
                        viewModel.rxDisposer.dispose()
                        context?.let { context -> circle.drawable.setTint(ContextCompat.getColor(context, R.color.positiveColor)) }
                    }else{
                        stopSession.visibility = View.GONE
                        time.text = "SESJA PRZERWANA"
                        time.textSize = 24f
                        context?.let { context -> circle.drawable.setTint(ContextCompat.getColor(context, R.color.negativeColor)) }
                    }
                    bindStopButton(it)

                }
            }
        })

        viewModel.timer.observe(viewLifecycleOwner, Observer {
            time.text = it.first
            if (it.second){
                viewModel.currentSession?.let {p ->
                    viewModel.rxDisposer.clear()
                    if (p.status == PomodoroStatus.ACTIVE.ordinal){
                        viewModel.increaseSession()
                    }else{
                        viewModel.continueSession()
                    }
                }

            }
        })
    }

    private fun bindStopButton(pomodoro: Pomodoro) {
        if (pomodoro.status == PomodoroStatus.ACTIVE.ordinal || pomodoro.status == PomodoroStatus.BREAK.ordinal) {
            stopSession.text = "STOP"
        } else if (pomodoro.status == PomodoroStatus.STOPPED.ordinal || pomodoro.status == PomodoroStatus.BREAK_STOPPED.ordinal) {
            stopSession.text = "WZNÓW"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.rxDisposer.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(viewModel.currentSession?.status != PomodoroStatus.FINISHED.ordinal && viewModel.currentSession?.status != PomodoroStatus.FAILED.ordinal){
            viewModel.localStorage.incrementFailedPomodoroSession()
            viewModel.setSessionFailedIfNeeded()
        }

    }

}