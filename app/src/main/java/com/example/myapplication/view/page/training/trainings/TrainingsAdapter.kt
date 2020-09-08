package com.example.myapplication.view.page.training.trainings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.model.WorkoutExercise
import com.example.myapplication.database.model.WorkoutExercises
import kotlinx.android.synthetic.main.item_empty_data.view.*
import kotlinx.android.synthetic.main.item_note.view.remove
import kotlinx.android.synthetic.main.item_training.view.*
import kotlinx.android.synthetic.main.item_workout_exercise.view.*
import org.joda.time.DateTime
import java.time.LocalDateTime

class TrainingsAdapter: RecyclerView.Adapter<TrainingsAdapter.VH>() {

    private var items: List<Any>? = null

    var onItemRemove: (id: Long) -> Unit = {}
    var onStartTraining: (id: Long) -> Unit = {}

    fun setData(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = when (viewType) {
            1 -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_training,
                parent,
                false
            )
            else -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_empty_data,
                parent,
                false
            )
        }
        return VH(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return when (items?.get(position)) {
            is WorkoutExercises -> 1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                holder.itemView.apply {
                    items?.get(position)?.let { workoutExercises ->
                        workoutExercises as WorkoutExercises
                        val date = DateTime(workoutExercises.date).toString("dd.MM.yyyy HH:mm")
                        trainingName.text = "Trening z dnia $date"

                        remove.setOnClickListener {
                            onItemRemove(workoutExercises.id)
                        }

                        when {
                            workoutExercises.isDone -> {
                                startLabel.text = "Wykonano trening"
                            }
                            workoutExercises.doneExercises > 0 -> {
                                startLabel.text = "Kontynuuj trening"
                            }
                            else -> {
                                startLabel.text = "Zacznij trening"
                            }
                        }

                        start.setOnClickListener {
                            onStartTraining(workoutExercises.id)
                        }

                        startLabel.setOnClickListener {
                            onStartTraining(workoutExercises.id)
                        }

                    }
                }
            }
            else -> {
                holder.itemView.apply {
                    message.text = "Brak treningów"
                }
            }
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}