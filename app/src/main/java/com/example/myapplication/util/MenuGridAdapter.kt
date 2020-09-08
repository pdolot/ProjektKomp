package com.example.myapplication.util

import android.app.Activity
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.model.MenuItem
import kotlinx.android.synthetic.main.item_menu.view.*

class MenuGridAdapter : RecyclerView.Adapter<MenuGridAdapter.VH>() {

    private var items: List<MenuItem>? = null

    fun setData(items: List<MenuItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            items?.get(position)?.let { menuItem ->
                menuTitle.text = menuItem.title

                Glide.with(context)
                    .load(menuItem.icon)
                    .centerCrop()
                    .into(menuIcon)

                setOnClickListener {
                    findNavController().navigate(menuItem.destination)
                }
            }
        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}