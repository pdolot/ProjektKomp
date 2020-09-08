package com.example.myapplication.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.example.myapplication.R
import com.example.myapplication.database.dao.PomodoroDao
import com.example.myapplication.di.Injector
import com.example.myapplication.view.customView.TopBar
import com.example.myapplication.view.navigation.NavigationAdapter
import com.example.myapplication.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CoreFragmentInterface {

    private val viewModel by lazy { MainActivityViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.deleteSession()

        navigation.setAdapter(NavigationAdapter(drawerLayout, findNavController(R.id.main_host_fragment)), viewModel.menuItems)

        topBar.onMenuClick = {
            openDrawer()
        }
    }

    private fun openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START, true)
    }

    override fun getTopBar(): TopBar = topBar
}
