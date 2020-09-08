package com.example.myapplication.viewModel.organizer.note

import androidx.lifecycle.*
import com.example.myapplication.database.dao.NoteDao
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotePageViewModel() : ViewModel() {

    @Inject
    lateinit var noteDao: NoteDao

    val notes = MutableLiveData<List<Any>>()

    init {
        Injector.component.inject(this)
    }

    fun deleteNote(id: Long) = viewModelScope.launch {
        noteDao.deleteById(id)
    }
}