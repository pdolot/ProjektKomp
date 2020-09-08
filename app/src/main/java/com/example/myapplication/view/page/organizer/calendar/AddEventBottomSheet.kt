package com.example.myapplication.view.page.organizer.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.base.BaseBottomSheet
import kotlinx.android.synthetic.main.bottom_sheet_add_event.*

class AddEventBottomSheet(val date: String) : BaseBottomSheet(){

    var onEventAdd: (eventName: String) -> Unit = {}

    init {
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventDate.text = date

        addEvent.setOnClickListener {
            onEventAdd(event.text.toString())
        }
    }
}