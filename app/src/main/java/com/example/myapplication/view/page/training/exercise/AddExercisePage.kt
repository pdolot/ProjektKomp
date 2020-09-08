package com.example.myapplication.view.page.training.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.training.exercise.AddExercisePageViewModel
import kotlinx.android.synthetic.main.page_add_exercise.*

class AddExercisePage : BaseFragment() {

    private val viewModel by lazy { AddExercisePageViewModel() }
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Lista ćwiczeń"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_add_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addExercise.setOnClickListener {
            if (exercise.text.toString().isNotBlank()){
                viewModel.insertExercise(exercise.text.toString())
            }else{
                Toast.makeText(context, "Podaj nazwę ćwiczenia", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.onInsert.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Dodano ćwiczenie", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        })
    }
}

