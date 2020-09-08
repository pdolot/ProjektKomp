package com.example.myapplication.view.page.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.meals.AddMealViewModel
import kotlinx.android.synthetic.main.page_add_meal.*

class AddMealPage : BaseFragment() {
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Dodaj posiłek"

    private val viewModel by lazy { AddMealViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_add_meal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectFromList.setOnClickListener {
            findNavController().navigate(R.id.toMealsList)
        }

        addMeal.setOnClickListener {
            val name = mealName.text.toString()
            val energyValue = energy.text.toString()
            val fatsValue = fats.text.toString()
            val proteinsValue = proteins.text.toString()
            val carbohydratesValue = carbohydrates.text.toString()

            if (!name.isNullOrBlank() && !energyValue.isNullOrBlank() && !fatsValue.isNullOrBlank() && !proteinsValue.isNullOrBlank() && !carbohydratesValue.isNullOrBlank()){
                viewModel.insertMeal(
                    name, energyValue.toInt(), proteinsValue.toInt(), fatsValue.toInt(), carbohydratesValue.toInt()
                )
            }else{
                Toast.makeText(context, "Wszystkie pola muszą być uzupełnione", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.onInsert.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Dodano posiłek", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        })
    }
}