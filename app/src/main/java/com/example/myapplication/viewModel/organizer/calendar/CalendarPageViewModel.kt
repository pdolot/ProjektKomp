package com.example.myapplication.viewModel.organizer.calendar

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.EventDao
import com.example.myapplication.di.Injector
import com.example.myapplication.database.model.EventDay
import kotlinx.coroutines.launch
import org.joda.time.LocalDateTime
import javax.inject.Inject


class CalendarPageViewModel : BaseViewModel() {

    @Inject
    lateinit var eventDao: EventDao

    lateinit var owner: LifecycleOwner

    var selectedDate: LocalDateTime = LocalDateTime.now()
    val events = MutableLiveData<List<Any>>()
    val monthEvents = MutableLiveData<List<EventDay>>()

    init {
        Injector.component.inject(this)
    }

    fun onSelectedDate(date: LocalDateTime) {
        this.selectedDate = date
        getEventsByDate(date)
    }

    fun onSelectedMonth(date: LocalDateTime) {
        getEventsByMonth(date.monthOfYear, date.year)
    }

    fun addEvent(name: String) {
        val date = LocalDateTime(
            selectedDate.year,
            selectedDate.monthOfYear,
            selectedDate.dayOfMonth,
            0,
            0
        )
        val event = EventDay(
            date = date.toDate().time,
            name = name,
            month = selectedDate.monthOfYear,
            year = selectedDate.year
        )
        insertEvent(event)
    }

    fun deleteEvent(id: Long) = viewModelScope.launch {
        eventDao.deleteById(id)
        getEventsByMonth(selectedDate.monthOfYear , selectedDate.year)
        getEventsByDate(selectedDate)
    }

    private fun getEventsByDate(selectedDate: LocalDateTime) = viewModelScope.launch {
        val date = LocalDateTime(
            selectedDate.year,
            selectedDate.monthOfYear,
            selectedDate.dayOfMonth,
            0,
            0
        )

        eventDao.getEventsByDate(date.toDate().time).let {
            if (it.isEmpty()) {
                events.postValue(listOf(false))
            } else {
                events.postValue(it)
            }
        }
    }

    private fun getEventsByMonth(month: Int, year: Int) = viewModelScope.launch {
        monthEvents.postValue(eventDao.getEventsByMoth(month, year))
    }

    private fun insertEvent(eventDay: EventDay) = viewModelScope.launch {
        eventDao.insert(eventDay)
        getEventsByMonth(selectedDate.monthOfYear , selectedDate.year)
        getEventsByDate(selectedDate)
    }
}