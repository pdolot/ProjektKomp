package com.example.myapplication.view.page.organizer.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.model.EventDay
import kotlinx.android.synthetic.main.item_event.view.*
import kotlinx.android.synthetic.main.item_meal.view.remove

class EventAdapter : RecyclerView.Adapter<EventAdapter.VH>() {

    private var items: List<Any>? = null

    var onItemRemove: (id: Long) -> Unit = {}

    fun setData(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = when(viewType){
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.item_empty_data, parent, false)
        }
        return VH(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return when(items?.get(position)){
            is EventDay -> 1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when(holder.itemViewType){
            1 -> {
                holder.itemView.apply {
                    items?.get(position)?.let { event ->
                        event as EventDay

                        eventName.text = event.name
                        remove.setOnClickListener {
                            onItemRemove(event.id)
                        }
                    }
                }
            }
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}