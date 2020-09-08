package com.example.myapplication.view.page.training.trainings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.page.training.newTraining.TrainingAdapter
import com.example.myapplication.viewModel.training.trainings.StartTrainingPageViewModel
import kotlinx.android.synthetic.main.page_workout_training.*

class StartTrainingPage : BaseFragment() {

    private val viewModel by lazy { StartTrainingPageViewModel() }
    private val adapter by lazy { TrainingAdapter(false) }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Trening"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_workout_training, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("ID") ?: 0

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@StartTrainingPage.adapter
        }

        viewModel.exercisesDao.getAllByParentId(id).observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        adapter.onStatusChanged = {id, status ->
            viewModel.update(id, status)
        }
    }
}


