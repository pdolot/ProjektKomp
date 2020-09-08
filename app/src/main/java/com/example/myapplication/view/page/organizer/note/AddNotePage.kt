package com.example.myapplication.view.page.organizer.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.organizer.note.AddNotePageViewModel
import kotlinx.android.synthetic.main.page_add_note.*

class AddNotePage : BaseFragment() {

    private val viewModel by lazy { AddNotePageViewModel() }
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Dodaj notatkę"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addNote.setOnClickListener {
            if (note.text.toString().isNotBlank()){
                viewModel.insertNote(note.text.toString())
            }
        }

        viewModel.onInsert.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Dodano notatke", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        })
    }
}

