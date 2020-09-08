package com.example.myapplication.view.page.organizer.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.organizer.calendar.CalendarPageViewModel
import kotlinx.android.synthetic.main.page_calendar.*
import org.joda.time.LocalDateTime

class CalendarPage : BaseFragment() {
    private val viewModel by lazy { CalendarPageViewModel() }
    private val adapter by lazy { EventAdapter() }

    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Kalendarz"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.owner = viewLifecycleOwner
        viewModel.onSelectedMonth(viewModel.selectedDate)

        eventRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CalendarPage.adapter
        }

        adapter.onItemRemove = {
            Toast.makeText(context, "Usunięto wydarzenie", Toast.LENGTH_SHORT).show()
            viewModel.deleteEvent(it)
        }

        addEvent.setOnClickListener {
            activity?.supportFragmentManager?.let {
                AddEventBottomSheet(
                    viewModel.selectedDate.toString("dd.MM.yyyy") ?: return@let
                ).apply {
                    onEventAdd = {
                        if (it.isNotBlank()) {
                            Toast.makeText(context, "Dodano wydarzenie", Toast.LENGTH_SHORT).show()
                            viewModel.addEvent(it)
                        }
                        this.dismiss()
                    }
                }.show(it)
            }

        }

        calendar.setCalendarListener(
            onMothChanged = {
                viewModel.onSelectedMonth(it)
                calendar.updateMonthLabel()
            },
            onSelectedDate = {
                if (it != null) {
                    viewModel.onSelectedDate(it)
                    eventsSection.visibility = View.VISIBLE
                    day.text = it.toString("dd.MM.yyyy")
                } else {
                    eventsSection.visibility = View.GONE
                }
            }
        )

        viewModel.onSelectedMonth(LocalDateTime.now())
        calendar.updateMonthLabel()
        viewModel.onSelectedDate(LocalDateTime.now())
    }

    override fun onResume() {
        super.onResume()

        viewModel.monthEvents.observe(viewLifecycleOwner, Observer {
            calendar.updateDays(it)
        })

        viewModel.events.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }

}

