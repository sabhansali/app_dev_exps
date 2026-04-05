package com.example.myapplication

import android.content.*
import android.database.sqlite.*

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "StudentDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE students (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "age INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    fun insertStudent(name: String, age: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("age", age)
        return db.insert("students", null, values) != -1L
    }

    fun getAllStudents(): MutableList<Student> {
        val list = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students", null)

        while (cursor.moveToNext()) {
            list.add(
                Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
                )
            )
        }
        cursor.close()
        return list
    }

    fun updateStudent(id: Int, name: String, age: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("age", age)
        return db.update("students", values, "id=?", arrayOf(id.toString())) > 0
    }

    fun deleteStudent(id: Int): Boolean {
        val db = writableDatabase
        return db.delete("students", "id=?", arrayOf(id.toString())) > 0
    }

    fun searchStudents(name: String): MutableList<Student> {
        val list = mutableListOf<Student>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM students WHERE name LIKE ?",
            arrayOf("%$name%")
        )

        while (cursor.moveToNext()) {
            list.add(
                Student(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
                )
            )
        }
        cursor.close()
        return list
    }
}