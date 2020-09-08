package com.example.myapplication.view.page.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.util.MenuGridAdapter
import com.example.myapplication.viewModel.training.TrainingPageViewModel
import kotlinx.android.synthetic.main.page_training.*

class TrainingPage : BaseFragment() {

    private val viewModel by lazy { TrainingPageViewModel() }
    private val adapter by lazy { MenuGridAdapter() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Trening"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_training, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@TrainingPage.adapter
        }

        adapter.setData(viewModel.menuItems)
    }
}

