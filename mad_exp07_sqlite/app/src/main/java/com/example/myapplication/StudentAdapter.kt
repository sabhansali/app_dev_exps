package com.example.myapplication

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private var list: MutableList<Student>,
    private val onDelete: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val age: TextView = view.findViewById(R.id.age)
        val deleteBtn: Button = view.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list[position]
        holder.name.text = student.name
        holder.age.text = "Age: ${student.age}"

        holder.deleteBtn.setOnClickListener {
            onDelete(student)
        }
    }

    fun updateList(newList: MutableList<Student>) {
        list = newList
        notifyDataSetChanged()
    }
}