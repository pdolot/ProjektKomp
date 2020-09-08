package com.example.myapplication.view.customView.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.item_day_name.view.*

class DayNameAdapter : RecyclerView.Adapter<DayNameAdapter.VH>() {

    private var names: List<String> = listOf("PN", "WT", "ŚR", "CZ", "PT", "SB", "ND")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_day_name, parent, false)
        return VH(
            view
        )
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            dayName.text = names[position]
        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}