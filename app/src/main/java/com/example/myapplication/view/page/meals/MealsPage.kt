package com.example.myapplication.view.page.meals

import android.graphics.Color
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
import com.example.myapplication.viewModel.meals.MealsPageViewModel
import kotlinx.android.synthetic.main.page_meals.*

class MealsPage : BaseFragment() {

    private val viewModel by lazy { MealsPageViewModel() }
    private val adapter by lazy { MealAdapter() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Posiłki"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_meals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTextWatchers()

        addMeal.setOnClickListener {
            findNavController().navigate(R.id.page_add_meal)
        }

        maxCalories.setText(viewModel.localStorage.getCaloriesOfDay().toString())
        maxFats.setText(viewModel.localStorage.getFatsOfDay().toString())
        maxProteins.setText(viewModel.localStorage.getProteinsOfDay().toString())
        maxCarbohydrates.setText(viewModel.localStorage.getCarbohydratesOfDay().toString())

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@MealsPage.adapter
        }

        adapter.onItemRemove = {
            Toast.makeText(context, "Usunięto posiłek", Toast.LENGTH_SHORT).show()
            viewModel.deleteMeal(it)
        }

        runObserver()
    }

    private fun addTextWatchers() {
        maxCalories.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()){
                    if (viewModel.calories.value ?: 0 > s.toString().toInt()){
                        currentCalories.setTextColor(Color.RED)
                    }else{
                        currentCalories.setTextColor(Color.WHITE)
                    }
                }else{
                    maxCalories.setText("0")
                }
            }
        })

        maxProteins.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()){
                    if (viewModel.proteins.value ?: 0 > s.toString().toInt()){
                        currentProteins.setTextColor(Color.RED)
                    }else{
                        currentProteins.setTextColor(Color.WHITE)
                    }
                }else{
                    maxProteins.setText("0")
                }
            }
        })

        maxFats.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()){
                    if (viewModel.fats.value ?: 0 > s.toString().toInt()){
                        currentFats.setTextColor(Color.RED)
                    }else{
                        currentFats.setTextColor(Color.WHITE)
                    }
                }else{
                    maxFats.setText("0")
                }
            }
        })

        maxCarbohydrates.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()){
                    if (viewModel.carbohydrates.value ?: 0 > s.toString().toInt()){
                        currentCarbohydrates.setTextColor(Color.RED)
                    }else{
                        currentCarbohydrates.setTextColor(Color.WHITE)
                    }
                }else{
                    maxCarbohydrates.setText("0")
                }
            }
        })
    }

   private fun runObserver(){
       viewModel.dao.getMealsByDay(viewModel.queryDate.toDate().time).observe(viewLifecycleOwner, Observer {
           if (it.isNotEmpty()){
               adapter.setData(it)
               viewModel.calories.postValue(it.map { it.energy }.sum())
               viewModel.fats.postValue(it.map { it.fats }.sum())
               viewModel.proteins.postValue(it.map { it.proteins }.sum())
               viewModel.carbohydrates.postValue(it.map { it.carbohydrates }.sum())
           }else{
               adapter.setData(listOf(false))
               viewModel.calories.postValue(0)
               viewModel.fats.postValue(0)
               viewModel.proteins.postValue(0)
               viewModel.carbohydrates.postValue(0)
           }
       })

       viewModel.calories.observe(viewLifecycleOwner, Observer {
           currentCalories.text = "$it/"
           if (it > maxCalories.text.toString().toInt()){
               currentCalories.setTextColor(Color.RED)
           }else{
               currentCalories.setTextColor(Color.WHITE)
           }
       })

       viewModel.fats.observe(viewLifecycleOwner, Observer {
           currentFats.text = "$it/"
           if (it > maxFats.text.toString().toInt()){
               currentFats.setTextColor(Color.RED)
           }else{
               currentFats.setTextColor(Color.WHITE)
           }
       })

       viewModel.proteins.observe(viewLifecycleOwner, Observer {
           currentProteins.text = "$it/"
           if (it > maxProteins.text.toString().toInt()){
               currentProteins.setTextColor(Color.RED)
           }else{
               currentProteins.setTextColor(Color.WHITE)
           }
       })

       viewModel.carbohydrates.observe(viewLifecycleOwner, Observer {
           currentCarbohydrates.text = "$it/"
           if (it > maxCarbohydrates.text.toString().toInt()){
               currentCarbohydrates.setTextColor(Color.RED)
           }else{
               currentCarbohydrates.setTextColor(Color.WHITE)
           }
       })
   }

    override fun onDestroyView() {
        viewModel.localStorage.setCaloriesOfDay(maxCalories.text.toString().toInt())
        viewModel.localStorage.setProteinsOfDay(maxProteins.text.toString().toInt())
        viewModel.localStorage.setFatsOfDay(maxFats.text.toString().toInt())
        viewModel.localStorage.setCarbohydratesOfDay(maxCarbohydrates.text.toString().toInt())
        super.onDestroyView()
    }
}

