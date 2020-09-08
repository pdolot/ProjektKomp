package com.example.myapplication.view.page.training.trainings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.training.trainings.PageTrainingsViewModel
import kotlinx.android.synthetic.main.page_trainings.*

class TrainingsPage : BaseFragment() {
    private val viewModel by lazy { PageTrainingsViewModel() }
    private val adapter by lazy { TrainingsAdapter() }
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Wybierz trening"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_trainings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@TrainingsPage.adapter
        }

        viewModel.exerciseDao.getAll().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                viewModel.trainings.postValue(emptyList())
            }else{
                viewModel.trainings.postValue(it)
            }
        })

        viewModel.trainings.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                adapter.setData(listOf(false))
            }else{
                it.forEach {training ->
                    viewModel.trainingExerciseDao.getAllByParentId(training.id).observe(viewLifecycleOwner, Observer {e ->
                        training.doneExercises = e.filter { it.done }.size
                        training.isDone = e.filter { it.done }.size == e.size
                        adapter.setData(it)
                    })
                }
            }
        })

        adapter.onItemRemove = {
            Toast.makeText(context, "Usunięto trening", Toast.LENGTH_SHORT).show()
            viewModel.trainings.postValue(emptyList())
            viewModel.removeById(it)
        }

        adapter.onStartTraining = {
            findNavController().navigate(R.id.page_start_training_page, bundleOf("ID" to it))
        }
    }
}


