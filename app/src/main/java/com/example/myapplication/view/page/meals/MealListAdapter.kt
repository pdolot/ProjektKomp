package com.example.myapplication.view.page.meals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.model.MealSet
import kotlinx.android.synthetic.main.item_empty_data.view.*
import kotlinx.android.synthetic.main.item_meal.view.*

class MealListAdapter : RecyclerView.Adapter<MealListAdapter.VH>() {

    private var items: List<Any>? = null

    var onItemSelected: (meal: MealSet) -> Unit = {}

    fun setData(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = when(viewType){
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_meal_from_list, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.item_empty_data, parent, false)
        }

        return VH(view)
    }

    override fun getItemViewType(position: Int): Int {
        return when(items?.get(position)){
            is MealSet -> 1
            else -> 0
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        when(holder.itemViewType){
            1->{
                holder.itemView.apply {
                    items?.get(position)?.let { meal ->
                        meal as MealSet
                        mealName.text = meal.name
                        energy.text = meal.energy.toString() + " kcal"
                        proteins.text = meal.proteins.toString() + " g"
                        fats.text = meal.fats.toString() + " g"
                        carbohydrates.text = meal.carbohydrates.toString() + " g"

                        setOnClickListener {
                            onItemSelected(meal)
                        }
                    }
                }
            }
            else -> {
                holder.itemView.apply {
                    message.text = "Brak posiłków"
                }
            }
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}