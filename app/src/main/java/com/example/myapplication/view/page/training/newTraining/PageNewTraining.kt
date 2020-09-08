package com.example.myapplication.view.page.training.newTraining

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
import com.example.myapplication.viewModel.training.newTraining.PageNewTrainingViewModel
import kotlinx.android.synthetic.main.page_new_training.*

class PageNewTraining : BaseFragment() {

    private val viewModel by lazy { PageNewTrainingViewModel() }
    private val adapter by lazy { TrainingAdapter(true) }
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Nowy trening"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.insert()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_new_training, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isAdding = false
        viewModel.isSaved = false

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@PageNewTraining.adapter
        }

        setTraining.setOnClickListener {
            viewModel.isSaved = true
            Toast.makeText(context, "Zapisano trening", Toast.LENGTH_SHORT).show()
        }

        addExercise.setOnClickListener {
            viewModel.isAdding = true
            findNavController().navigate(R.id.page_add_exercise_to_new_raining, bundleOf("ID" to viewModel.trainingId.value))
        }

        viewModel.trainingId.observe(viewLifecycleOwner, Observer {
            viewModel.getExercises(viewLifecycleOwner)
        })

        viewModel.exercises.observe(viewLifecycleOwner, Observer {
            setTraining.isEnabled = it.isNotEmpty()
            viewModel.isAdding = !it.isEmpty()
            if (it.isEmpty()){
                adapter.setData(listOf(false))
            }else{
                adapter.setData(it)
            }
        })

        adapter.onItemRemove = {
            Toast.makeText(context, "Usunięto ćwiczenie z treningu", Toast.LENGTH_SHORT).show()
            viewModel.deleteExercise(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!viewModel.isSaved) {
            Toast.makeText(context, "Nie zapisano treningu", Toast.LENGTH_SHORT).show()
            viewModel.delete()
        }
    }
}


