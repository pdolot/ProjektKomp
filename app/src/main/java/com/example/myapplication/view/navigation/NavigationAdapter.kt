package com.example.myapplication.view.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.MenuItem
import kotlinx.android.synthetic.main.item_navigation_menu.view.*

class NavigationAdapter(var drawer: DrawerLayout, var navController: NavController) :
    RecyclerView.Adapter<NavigationAdapter.VH>() {

    private var items: List<MenuItem>? = null

    fun setData(items: List<MenuItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_navigation_menu, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            items?.get(position)?.let { menuItem ->
                menuTitle.text = menuItem.title
                setOnClickListener {
                    drawer.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
                        override fun onDrawerClosed(drawerView: View) {
                            drawer.removeDrawerListener(this)
                            if (navController.currentDestination?.id != menuItem.destination) {
                                navController.navigate(menuItem.destination)
                            }
                            super.onDrawerClosed(drawerView)
                        }
                    })
                    drawer.closeDrawer(GravityCompat.START, true)
                }
            }
        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}