package com.example.myapplication.view.page.training.newTraining

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.model.WorkoutExercise
import kotlinx.android.synthetic.main.item_empty_data.view.*
import kotlinx.android.synthetic.main.item_note.view.remove
import kotlinx.android.synthetic.main.item_workout_exercise.view.*

class TrainingAdapter(var removable: Boolean) : RecyclerView.Adapter<TrainingAdapter.VH>() {

    private var items: List<Any>? = null

    var onItemRemove: (id: Long) -> Unit = {}
    var onStatusChanged: (id: Long, status: Boolean) -> Unit = {_, _ ->}

    fun setData(items: List<Any>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = when (viewType) {
            1 -> LayoutInflater.from(parent.context).inflate(
                R.layout.item_workout_exercise,
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
            is WorkoutExercise -> 1
            else -> 0
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                holder.itemView.apply {
                    items?.get(position)?.let { workoutExercise ->
                        workoutExercise as WorkoutExercise
                        exerciseName.text = workoutExercise.exerciseName
                        repeatCount.text = workoutExercise.repeatCount.toString()

                        if (!removable){
                            remove.visibility = View.INVISIBLE
                            remove.isEnabled = false
                            status.visibility = View.VISIBLE
                            status.isChecked = workoutExercise.done
                            status.setOnClickListener {
                                onStatusChanged(workoutExercise.id, !workoutExercise.done)
                            }
                        }else{
                            status.visibility = View.GONE
                            remove.visibility = View.VISIBLE
                            remove.isEnabled = true
                            remove.setOnClickListener {
                                onItemRemove(workoutExercise.id)
                            }
                        }

                        if (workoutExercise.load != null) {
                            load.text = workoutExercise.load.toString() + " kg"
                            load.visibility = View.VISIBLE
                            loadLabel.visibility = View.VISIBLE
                        } else {
                            load.visibility = View.GONE
                            loadLabel.visibility = View.GONE
                        }

                        if (workoutExercise.time != null) {
                            time.text = workoutExercise.time.toString() + "min"
                            time.visibility = View.VISIBLE
                            timeLabel.visibility = View.VISIBLE
                        } else {
                            time.visibility = View.GONE
                            timeLabel.visibility = View.GONE
                        }


                    }
                }
            }
            else -> {
                holder.itemView.apply {
                    message.text = "Brak ćwiczeń"
                }
            }
        }

    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}