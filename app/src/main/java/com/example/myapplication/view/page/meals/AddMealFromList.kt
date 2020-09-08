package com.example.myapplication.view.page.meals

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.database.model.MealSet
import com.example.myapplication.viewModel.meals.AddMealFromListViewModel
import kotlinx.android.synthetic.main.page_meals_add_from_list.*

class AddMealFromList : BaseFragment() {
    private val viewModel by lazy { AddMealFromListViewModel() }
    private val adapter by lazy { MealListAdapter() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Lista posiłków"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_meals_add_from_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@AddMealFromList.adapter
        }

        viewModel.mealsSetDao.getAllMeals().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                adapter.setData(listOf(false))
                viewModel.meals = emptyList()
            } else {
                adapter.setData(it)
                viewModel.meals = it
            }
        })

        adapter.onItemSelected = {
            viewModel.insert(it)
        }

        viewModel.onInsert.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Dodano posiłek", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        })


        searchMeal.addTextChangedListener( object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.setData(viewModel.filterList(s.toString()))
            }

        })
    }
}


