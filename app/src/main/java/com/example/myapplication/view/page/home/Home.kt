package com.example.myapplication.view.page.home

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.view.customView.Indicator
import com.example.myapplication.viewModel.home.HomeViewModel
import kotlinx.android.synthetic.main.page_home.*

class Home : BaseFragment() {
    private val viewModel by lazy { HomeViewModel() }
    private lateinit var adapter: MenuAdapter
    private val snapHelper by lazy { LinearSnapHelper() }
    private var indicatorSize = 48
    private var indicatorMargin = 24


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_home, container, false)
    }

    override var sideMenuEnabled: Boolean = false
    override var topBarTitle: String = "Strona domowa"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        indicatorSize = resources.getDimensionPixelSize(R.dimen.indicator)

        activity?.let {
            adapter = MenuAdapter(it)

            recyclerView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = this@Home.adapter
                snapHelper.attachToRecyclerView(this)
            }

            adapter.setData(viewModel.menuItems)
            bindIndicators()
            setCurrentIndicator(viewModel.currentItem)
        }

        setListeners()

        if (viewModel.localStorage.getLastBodyWeightChange() != viewModel.todayInMilliseconds){
            MaterialDialog(context ?: return).show{
                title(text = "Dzienny raport wagi")
                input(inputType = (InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL), maxLength = 6, waitForPositiveButton = true, hint = "Podaj obecną wagę", allowEmpty = false) { dialog, text ->
                    viewModel.localStorage.setNewBodyWeight(text.toString().toFloat())
                }
                positiveButton(text = "Dodaj")
                negativeButton(text = "Anuluj")
            }
        }
    }

    private fun setListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lm = recyclerView.layoutManager ?: return
                val sw = snapHelper.findSnapView(lm)
                sw?.let {
                    val position = lm.getPosition(sw)
                    setCurrentIndicator(position)
                }
            }
        })
    }

    private fun setCurrentIndicator(position: Int) {
        val prev = tabIndicator.getChildAt(viewModel.currentItem)
        prev.scaleX = 0.75f
        prev.scaleY = 0.75f
        prev.alpha = 0.5f

        val next = tabIndicator.getChildAt(position)
        next.scaleX = 1.0f
        next.scaleY = 1.0f
        next.alpha = 1.0f
        viewModel.currentItem = position
    }

    private fun bindIndicators() {
        tabIndicator.removeAllViews()
        for (i in viewModel.menuItems.indices) {
            val v = Indicator(context ?: return, indicatorSize, indicatorSize, indicatorMargin)
            v.scaleX = 0.75f
            v.scaleY = 0.75f
            v.alpha = 0.5f
            tabIndicator.addView(v)
        }
    }
}


