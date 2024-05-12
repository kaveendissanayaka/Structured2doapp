package com.example.structured

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.structured.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db :NotedatabaseHelper
    private lateinit var notesAdapter : NotesAdapter
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotedatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(),this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter


        binding.addbtn.setOnClickListener{
            val intent = Intent(this@MainActivity,AddNoteActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onResume(){
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}