package com.example.myapplication

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var db: DBHelper
    lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main) // ✅ NOW WORKS

        db = DBHelper(this)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val ageInput = findViewById<EditText>(R.id.ageInput)
        val searchInput = findViewById<EditText>(R.id.searchInput)

        val addBtn = findViewById<Button>(R.id.addBtn)
        val searchBtn = findViewById<Button>(R.id.searchBtn)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = StudentAdapter(db.getAllStudents()) {
            db.deleteStudent(it.id)
            refreshData()
        }

        recyclerView.adapter = adapter

        addBtn.setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString().toIntOrNull()

            if (name.isNotEmpty() && age != null) {
                db.insertStudent(name, age)
                refreshData()
                nameInput.text.clear()
                ageInput.text.clear()
            }
        }

        searchBtn.setOnClickListener {
            val query = searchInput.text.toString()
            adapter.updateList(db.searchStudents(query))
        }
    }

    private fun refreshData() {
        adapter.updateList(db.getAllStudents())
    }
}