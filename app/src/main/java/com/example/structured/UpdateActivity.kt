package com.example.structured

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import com.example.structured.databinding.ActivityUpdateNoteBinding
import java.util.Calendar


class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db : NotedatabaseHelper
    private var noteId :Int  = -1
    private lateinit var updateDeadlineDatePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotedatabaseHelper(this)

        updateDeadlineDatePicker = findViewById(R.id.updateDeadlineDatePicker)


        noteId = intent.getIntExtra("note_id",-1)
        if(noteId==-1){
            finish()
            return
        }


        val note = db.getNoteById(noteId)
        binding.editnotetitle.setText(note.title)
        binding.editdescription.setText(note.content)
        val calendar = Calendar.getInstance().apply {
            timeInMillis = note.deadline
        }
        updateDeadlineDatePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ) { _, year, monthOfYear, dayOfMonth ->
            // Handle date change if needed
        }

        binding.updatebtn.setOnClickListener{
            val newtitle = binding.editnotetitle.text.toString()
            val newcontent = binding.editdescription.text.toString()
            val newDeadline = getSelectedDeadline()
            val updateNote = Note(noteId,newtitle,newcontent,newDeadline)

            db.updateNote(updateNote)
            finish()
            Toast.makeText(this,"Change Saved",Toast.LENGTH_SHORT).show()

        }

        binding.backbtn2.setOnClickListener{
            val intent = Intent(this@UpdateActivity,MainActivity::class.java)
            startActivity(intent)

        }
    }

    private fun getSelectedDeadline(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(
            updateDeadlineDatePicker.year,
            updateDeadlineDatePicker.month,
            updateDeadlineDatePicker.dayOfMonth
        )
        return calendar.timeInMillis
    }
}