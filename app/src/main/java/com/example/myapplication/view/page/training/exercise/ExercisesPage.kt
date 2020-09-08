package com.example.myapplication.view.page.training.exercise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.training.exercise.ExercisesPageViewModel
import kotlinx.android.synthetic.main.page_exercises.*

class ExercisesPage : BaseFragment() {
    private val viewModel by lazy { ExercisesPageViewModel() }
    private val adapter by lazy { ExercisesAdapter() }
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Lista ćwiczeń"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_exercises, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ExercisesPage.adapter
        }

        adapter.onItemRemove = {
            Toast.makeText(context, "Usunięto ćwiczenie", Toast.LENGTH_SHORT).show()
            viewModel.delete(it)
        }

        addExercise.setOnClickListener {
            findNavController().navigate(R.id.page_add_exercise)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.exercisesDao.getAll().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                adapter.setData(listOf(false))
            }else{
                adapter.setData(it)
            }
        })
    }
}

