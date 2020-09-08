package com.example.myapplication.view.page.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.raport.ReportPageViewModel
import kotlinx.android.synthetic.main.page_report.*

class ReportPage : BaseFragment() {

    private val viewModel by lazy { ReportPageViewModel() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Raport"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val current = viewModel.localStorage.getCurrentBodyWeight()
        val prev = viewModel.localStorage.getPreviousBodyWeight()

        if (current == -1f && prev == -1f){
            weightValue.text = "Brak informacji o wadze"
        }else{
            if (prev == -1f){
                weightValue.text = getString(R.string.weightReportWithoutPrevious, current)
            }else{
                weightValue.text = getString(R.string.weightReport, current, prev)
            }
        }

        pomodoroValue.text = getString(R.string.pomodoroReport, viewModel.localStorage.getPassedPomodoroSession(), viewModel.localStorage.getFailedPomodoroSession())

        viewModel.trainingDao.getTrainingCount().observe(viewLifecycleOwner, Observer {
            viewModel.trainingCount.postValue(it)
        })

        viewModel.trainingExerciseDao.getAll().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                viewModel.doneTrainingCount.postValue(0)
            }else{
                var done = 0
                it.groupBy { it.parentId }.forEach {
                    var count = it.value.filter { it.done }.size
                    if (count == it.value.size){
                        done++
                    }
                }
                viewModel.doneTrainingCount.postValue(done)
            }
        })

        viewModel.trainingCount.observe(viewLifecycleOwner, Observer {
            trainingValue.text = getString(R.string.trainingReport, viewModel.doneTrainingCount.value, it)
        })

        viewModel.doneTrainingCount.observe(viewLifecycleOwner, Observer {
            trainingValue.text = getString(R.string.trainingReport, it, viewModel.trainingCount.value)
        })
    }
}
