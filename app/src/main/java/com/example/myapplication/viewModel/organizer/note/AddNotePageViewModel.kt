package com.example.myapplication.viewModel.organizer.note

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.database.dao.NoteDao
import com.example.myapplication.database.model.Note
import com.example.myapplication.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddNotePageViewModel : BaseViewModel() {

    @Inject
    lateinit var noteDao: NoteDao

    init {
        Injector.component.inject(this)
    }

    val onInsert = MutableLiveData<Any>()

    fun insertNote(content: String) = viewModelScope.launch {
        noteDao.insert(Note(content = content))
        onInsert.postValue(true)
    }
}
