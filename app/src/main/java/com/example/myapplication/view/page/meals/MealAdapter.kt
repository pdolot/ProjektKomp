package com.example.myapplication.view.page.meals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.model.Meal
import kotlinx.android.synthetic.main.item_empty_data.view.*
import kotlinx.android.synthetic.main.item_meal.view.*

class MealAdapter : RecyclerView.Adapter<MealAdapter.VH>() {

    private var items: List<Any>? = null

    var onItemRemove: (id: Long) -> Unit = {}

    fun setData(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = when(viewType){
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.item_empty_data, parent, false)
        }
            
        return VH(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return when(items?.get(position)){
            is Meal -> 1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when(holder.itemViewType){
            1 -> {
                holder.itemView.apply {
                    items?.get(position)?.let { meal ->
                        meal as Meal
                        mealName.text = meal.name
                        energy.text = meal.energy.toString() + " kcal"
                        proteins.text = meal.proteins.toString() + " g"
                        fats.text = meal.fats.toString() + " g"
                        carbohydrates.text = meal.carbohydrates.toString() + " g"

                        remove.setOnClickListener {
                            meal.id?.let { onItemRemove(meal.id) }
                        }
                    }
                }
            }
            0 -> {
                holder.itemView.apply { 
                    message.text = "Brak posiłków na dziś"
                }
            }
        }
        
    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}