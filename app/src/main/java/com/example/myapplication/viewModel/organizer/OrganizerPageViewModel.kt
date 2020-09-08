package com.example.myapplication.viewModel.organizer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.PomodoroDao
import com.example.myapplication.di.Injector
import com.example.myapplication.model.MenuItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrganizerPageViewModel : BaseViewModel() {

    @Inject
    lateinit var pomodoroDao: PomodoroDao

    var menuItems = MutableLiveData<List<MenuItem>>()

    init {
        Injector.component.inject(this)
    }

    fun checkForAnyActivePomodoroSession() = viewModelScope.launch {
        pomodoroDao.isAnySession().size.let {
            menuItems.postValue( if (it > 0){
                listOf(
                    MenuItem("Kalendarz", R.id.page_calendar, icon = R.drawable.ic_event_note),
                    MenuItem("Notatnik", R.id.page_notes, icon = R.drawable.ic_note),
                    MenuItem("Pomodoro", R.id.page_pomodoro_active, icon = R.drawable.ic_pomodoro)
                )
            }else{
                listOf(
                    MenuItem("Kalendarz", R.id.page_calendar, icon = R.drawable.ic_event_note),
                    MenuItem("Notatnik", R.id.page_notes, icon = R.drawable.ic_note),
                    MenuItem("Pomodoro", R.id.page_pomodoro, icon = R.drawable.ic_pomodoro)
                )
            })
        }
    }


}
