package com.example.myapplication.view.page.home

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
import kotlinx.android.synthetic.main.item_menu_home.view.*

class MenuAdapter(var activity: Activity) : RecyclerView.Adapter<MenuAdapter.VH>() {

    private var items: List<MenuItem>? = null

    fun setData(items: List<MenuItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_menu_home, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            items?.get(position)?.let { menuItem ->
                menuTitle.text = menuItem.title

                Glide.with(context)
                    .load(menuItem.bgImage)
                    .centerCrop()
                    .into(bgImage)

                setOnClickListener {
                    findNavController().navigate(menuItem.destination)
                }
            }

            (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
                val point = Point().apply { activity.windowManager.defaultDisplay.getSize(this) }
                width = (point.x * 0.74).toInt()

                marginStart =
                    if (position == 0) (point.x * 0.13).toInt() else (point.x * 0.02).toInt()
                marginEnd =
                    if (position == (itemCount - 1)) (point.x * 0.13).toInt() else (point.x * 0.02).toInt()
            }


        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}