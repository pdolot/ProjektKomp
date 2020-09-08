package com.example.myapplication.view.page.organizer.pomodoro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.organizer.pomodoro.PomodoroPageViewModel
import kotlinx.android.synthetic.main.page_pomodoro.*

class PomodoroPage : BaseFragment() {
    private val viewModel by lazy { PomodoroPageViewModel() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Pomodoro"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_pomodoro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startSession.setOnClickListener {
            val sCount = sessionCount.text.toString()
            val sLength = sessionLength.text.toString()
            val bLength = sessionBreakLength.text.toString()

            if (sCount.isNotBlank() && sLength.isNotBlank() && bLength.isNotBlank()){
                // time need to be in seconds
                viewModel.insert(sCount.toInt(), sLength.toLong() * 60, bLength.toLong() * 60)
            }else{
                Toast.makeText(context, "Wszystkie pola muszą być uzupełnione", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.pomodoroDao.getSession().observe(viewLifecycleOwner, Observer {
            if (it.any()){
                findNavController().navigate(R.id.action_page_pomodoro_to_page_pomodoro_active)
            }
        })
    }

}


