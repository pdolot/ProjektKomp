package com.example.myapplication.view.navigation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.model.MenuItem
import kotlinx.android.synthetic.main.navigation.view.*

class Navigation @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.navigation, this)
    }

    fun setAdapter(a: NavigationAdapter, items: List<MenuItem>){
        navigationRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = a
        }

        a.setData(items)
    }
}