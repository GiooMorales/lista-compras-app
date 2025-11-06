package br.gov.sp.etec.lista.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.gov.sp.etec.lista.R
import br.gov.sp.etec.lista.UI.itemForm
import br.gov.sp.etec.lista.models.Task

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    //ViewHolder: Classe interna que representa o layout de cada item
    inner class TaskViewHolder(view: View): RecyclerView.ViewHolder(view) {
        //val checkBoxCompleted: CheckBox = view.findViewById()
        var textViewItemTitle: TextView = view.findViewById(R.id.textViewItemTitle)
        var textViewItemDescription: TextView = view.findViewById(R.id.textViewItemDescription)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition;
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    val intent = Intent(view.context, itemForm::class.java)
                    intent.putExtra("id", task.id)
                    intent.putExtra("title", task.title)
                    intent.putExtra("description", task.description)
                    intent.putExtra("status", task.status)

                    view.context.startActivity(intent)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        //holder.checkBoxCompleted
        holder.textViewItemTitle.text = task.title
        holder.textViewItemDescription.text = task.description
    }
}