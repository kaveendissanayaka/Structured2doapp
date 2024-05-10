package com.example.structured

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.structured.databinding.ActivityUpdateNoteBinding


class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db : NotedatabaseHelper
    private var noteId :Int  = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotedatabaseHelper(this)

        noteId = intent.getIntExtra("note_id",-1)
        if(noteId==-1){
            finish()
            return
        }


        val note = db.getNoteById(noteId)
        binding.editnotetitle.setText(note.title)
        binding.editdescription.setText(note.content)

        binding.updatebtn.setOnClickListener{
            val newtitle = binding.editnotetitle.text.toString()
            val newcontent = binding.editdescription.text.toString()
            val updateNote = Note(noteId,newtitle,newcontent)

            db.updateNote(updateNote)
            finish()
            Toast.makeText(this,"Change Saved",Toast.LENGTH_SHORT).show()

        }
    }
}