package com.example.myapplication.view.customView.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.CalendarDay
import kotlinx.android.synthetic.main.item_day.view.*
import org.joda.time.LocalDateTime

class CalendarViewAdapter : RecyclerView.Adapter<CalendarViewAdapter.VH>() {

    private var days: List<Any>? = null
    private var selectedDay = -1

    var onSelectedDay: (LocalDateTime?) -> Unit = {}

    fun setData(days: List<Any>, selectedDay: Int) {
        this.days = days
        this.selectedDay = selectedDay
        if (selectedDay < 0){
            onSelectedDay(null)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = when (viewType) {
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_empty_day,
                parent,
                false
            )
        }
        return VH(
            view
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (days?.get(position)) {
            is CalendarDay -> 1
            else -> 0
        }
    }

    override fun getItemCount(): Int = days?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                val day = days?.get(position) as CalendarDay
                day.let { day ->
                    holder.itemView.apply {
                        isEvent.visibility = if (day.isEvent) View.VISIBLE else View.GONE

                        background = if (position == selectedDay) {
                            resources.getDrawable(R.drawable.selected_day_background, null)
                        } else {
                            null
                        }

                        calendarDay.text = day.day.toString("d")
                        setOnClickListener {
                            if (selectedDay != -1) {
                                notifyItemChanged(selectedDay)
                            }
                            selectedDay = position
                            onSelectedDay(day.day)
                            notifyItemChanged(selectedDay)
                        }

                    }
                }
            }
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}