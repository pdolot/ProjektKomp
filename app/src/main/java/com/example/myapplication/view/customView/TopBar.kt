package com.example.myapplication.view.customView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import kotlinx.android.synthetic.main.view_topbar.view.*

class TopBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var isMenuEnabled = false
        set(value) {
            field = value
            sideMenu.visibility = if (value) View.VISIBLE else View.INVISIBLE
            sideMenu.isClickable = value
        }

    var onMenuClick: () -> Unit = {}

    init {
        View.inflate(context, R.layout.view_topbar, this)

        sideMenu.setOnClickListener {
            onMenuClick()
        }
    }

    fun setTitle(t: String){
        title.text = t
    }
}