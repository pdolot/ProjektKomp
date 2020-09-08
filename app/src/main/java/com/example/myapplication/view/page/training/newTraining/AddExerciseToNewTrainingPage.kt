package com.example.myapplication.view.page.training.newTraining

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.training.newTraining.AddExerciseToNewTrainingPageViewModel
import kotlinx.android.synthetic.main.page_add_workout_exercise.*

class AddExerciseToNewTrainingPage : BaseFragment() {

    private val viewModel by lazy { AddExerciseToNewTrainingPageViewModel() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Dodaj ćwiczenie"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_add_workout_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.parentId = arguments?.getLong("ID")

        viewModel.exercisesDao.getAll().observe(viewLifecycleOwner, Observer {
            context?.let { c ->
                val adapter = ArrayAdapter<String>(
                    c,
                    android.R.layout.simple_dropdown_item_1line,
                    it.map { it.exerciseName })
                exerciseName.setAdapter(adapter)
            }
        })

        addWorkoutExercise.setOnClickListener {
            val name = exerciseName.text.toString()
            val rCount = repeatCount.text.toString()

            if (name.isNotBlank() && rCount.isNotBlank()){
                viewModel.insert(
                    name,
                    rCount.toInt(),
                    if (weight.text.toString().isNotBlank()) weight.text.toString().toFloat() else null,
                    if (time.text.toString().isNotBlank()) time.text.toString().toLong() else null,
                    if (seriesCount.text.toString().isNotBlank()) seriesCount.text.toString().toInt() else 1
                )

            }
        }

        viewModel.onInsert.observe(viewLifecycleOwner, Observer {
            if (it == 1){
                Toast.makeText(context, "Dodano ćwiczenie do treningu", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context, "Dodano ćwiczenia do treningu", Toast.LENGTH_SHORT).show()
            }

            findNavController().popBackStack()
        })
    }
}


