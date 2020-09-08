package com.example.myapplication.view.page.organizer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.util.MenuGridAdapter
import com.example.myapplication.viewModel.organizer.OrganizerPageViewModel
import kotlinx.android.synthetic.main.page_organizer.*

class OrganizerPage : BaseFragment() {

    private val viewModel by lazy { OrganizerPageViewModel() }
    private val adapter by lazy { MenuGridAdapter() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Organizer"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_organizer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@OrganizerPage.adapter
        }

        viewModel.menuItems.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.checkForAnyActivePomodoroSession()
    }
}

