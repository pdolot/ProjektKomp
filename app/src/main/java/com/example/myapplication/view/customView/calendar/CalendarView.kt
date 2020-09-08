package com.example.myapplication.view.customView.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.database.model.EventDay
import com.example.myapplication.model.CalendarDay
import kotlinx.android.synthetic.main.view_calendar.view.*
import org.joda.time.LocalDateTime

class CalendarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val adapter by lazy { CalendarViewAdapter() }
    private val dayNamesAdapter by lazy { DayNameAdapter() }
    private val months = listOf(
        "Styczeń",
        "Luty",
        "Marzec",
        "Kwiecień",
        "Maj",
        "Czerwiec",
        "Lipiec",
        "Sierpień",
        "Wrzesień",
        "Październik",
        "Listopad",
        "Grudzień"
    )

    private var selectedMonth = LocalDateTime.now()
    private var selectedDay = LocalDateTime.now()

    private var calendarListener: CalendarListener? = null

    init {
        View.inflate(context, R.layout.view_calendar, this)

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = this@CalendarView.adapter
        }

        daysRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = this@CalendarView.dayNamesAdapter
        }

        adapter.onSelectedDay = {
            selectedDay = it
            calendarListener?.onSelectedDate(it)
        }

        setListeners()
    }

    private fun setListeners() {
        prevMonth.setOnClickListener {
            selectedMonth = selectedMonth.minusMonths(1)
            calendarListener?.onMonthChanged(selectedMonth)
        }

        nextMonth.setOnClickListener {
            selectedMonth = selectedMonth.plusMonths(1)
            calendarListener?.onMonthChanged(selectedMonth)
        }
    }

    fun updateMonthLabel() {
        month.text = "${months[selectedMonth.monthOfYear - 1]}, ${selectedMonth.year}"
    }

    fun updateDays(events: List<EventDay>) {
        val days = ArrayList<Any>()
        val daysInMonth = selectedMonth.dayOfMonth().maximumValue
        val dayOfWeek = selectedMonth.dayOfMonth().withMinimumValue().dayOfWeek - 1
        for (i in 0 until dayOfWeek) {
            days.add(false)
        }

        var selectedDayIndex = -1

        for (i in 0 until daysInMonth) {
            val day = selectedMonth.dayOfMonth().withMinimumValue().plusDays(i)
            val isEvent = checkEventsAtDay(events, day)
            days.add(CalendarDay(day, isEvent))
            if (selectedDay != null){
                if (day.year == selectedDay.year && day.monthOfYear == selectedDay.monthOfYear && day.dayOfMonth == selectedDay.dayOfMonth){
                    selectedDayIndex = days.size - 1
                }
            }
        }
        adapter.setData(days, selectedDayIndex)
    }

    private fun checkEventsAtDay(events: List<EventDay>, day: LocalDateTime): Boolean {
        val date = LocalDateTime(day.year, day.monthOfYear, day.dayOfMonth, 0, 0).toDate().time
        for (i in events.indices){
            if (events[i].date == date){
                return true
            }
        }
        return false
    }

    fun setCalendarListener(calendarListener: CalendarListener) {
        this.calendarListener = calendarListener
    }

    fun setCalendarListener(
        onMothChanged: (month: LocalDateTime) -> Unit,
        onSelectedDate: (day: LocalDateTime?) -> Unit
    ) {
        setCalendarListener(object : CalendarListener {
            override fun onMonthChanged(month: LocalDateTime) {
                onMothChanged(month)
            }

            override fun onSelectedDate(day: LocalDateTime?) {
                onSelectedDate(day)
            }

        })
    }

    interface CalendarListener {
        fun onMonthChanged(month: LocalDateTime)
        fun onSelectedDate(day: LocalDateTime?)
    }
}