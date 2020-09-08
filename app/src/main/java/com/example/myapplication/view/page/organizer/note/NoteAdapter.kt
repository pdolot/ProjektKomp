package com.example.myapplication.view.page.organizer.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.model.Note
import kotlinx.android.synthetic.main.item_empty_data.view.*
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.VH>() {

    private var items: List<Any>? = null

    var onItemRemove: (id: Long) -> Unit = {}

    fun setData(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = when(viewType){
            1 -> LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.item_empty_data, parent, false)
        }
        return VH(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return when(items?.get(position)){
            is Note -> 1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when(holder.itemViewType){
            1 -> {
                holder.itemView.apply {
                    items?.get(position)?.let { note ->
                        note as Note
                        noteContent.text = note.content
                        remove.setOnClickListener {
                            onItemRemove(note.id)
                        }
                    }
                }
            }
            else -> {
                holder.itemView.apply {
                    message.text = "Brak notatek"
                }
            }
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}