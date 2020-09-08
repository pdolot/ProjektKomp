package com.example.myapplication.view.page.organizer.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.viewModel.organizer.note.NotePageViewModel
import kotlinx.android.synthetic.main.page_notes.*

class NotePage : BaseFragment() {
    private val viewModel by lazy { NotePageViewModel() }
    private val adapter by lazy { NoteAdapter() }
    override var sideMenuEnabled: Boolean = true
    override var topBarTitle: String = "Notatki"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.page_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNote.setOnClickListener {
            findNavController().navigate(R.id.page_add_note)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@NotePage.adapter
        }

        adapter.onItemRemove = {
            Toast.makeText(context, "Usunięto notatke", Toast.LENGTH_SHORT).show()
            viewModel.deleteNote(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.noteDao.getAll().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                adapter.setData(it)
            }else{
                adapter.setData(listOf(false))
            }

        })
    }
}


