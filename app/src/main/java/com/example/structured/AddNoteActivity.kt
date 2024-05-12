package com.example.structured

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.structured.databinding.ActivityAddNoteBinding
import java.util.*


class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotedatabaseHelper
    private lateinit var deadlineDatePicker: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotedatabaseHelper(this)

        deadlineDatePicker = findViewById(R.id.deadlineDatePicker)

        binding.savebtn.setOnClickListener{
            val title = binding.notetitle.text.toString()
            val content = binding.description.text.toString()
            val deadline = getSelectedDeadline()

            val note = Note(0, title, content, deadline)
            db.insertNote(note)
            finish()
            Toast.makeText(this,"note saved",Toast.LENGTH_SHORT).show()

        }

        binding.backbtn.setOnClickListener{
            val intent = Intent(this@AddNoteActivity,MainActivity::class.java)
            startActivity(intent)

        }
    }

    private fun getSelectedDeadline(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, deadlineDatePicker.year)
        calendar.set(Calendar.MONTH, deadlineDatePicker.month)
        calendar.set(Calendar.DAY_OF_MONTH, deadlineDatePicker.dayOfMonth)
        return calendar.timeInMillis
    }
}