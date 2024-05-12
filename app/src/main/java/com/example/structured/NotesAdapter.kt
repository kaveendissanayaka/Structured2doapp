package com.example.structured
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import android.annotation.SuppressLint
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes:List<Note>,context :Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db : NotedatabaseHelper = NotedatabaseHelper(context)
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    class NoteViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val titletextView : TextView = itemView.findViewById(R.id.titletext)
        val contenttextView : TextView = itemView.findViewById(R.id.contenttextview)
        val deadlinetextview : TextView = itemView.findViewById(R.id.deadlinetextview)
        val updateButton : ImageButton = itemView.findViewById(R.id.updatebtn)
        val deleteButton : ImageButton = itemView.findViewById(R.id.deletebtn)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linearlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)

    }

    override fun getItemCount(): Int  = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titletextView.text = note.title
        holder.contenttextView.text = note.content
        holder.deadlinetextview.text = "Deadline: ${formatDeadline(note.deadline)}"

        // Set initial checkbox state


        // Checkbox click listener
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            val adapterPosition = holder.adapterPosition
            val checkedNote = notes[adapterPosition]

            // Move the item to the bottom if checked
            if (isChecked) {
                val newList = mutableListOf<Note>().apply {
                    addAll(notes)
                    removeAt(adapterPosition)
                    add(checkedNote)
                }
                notes = newList
                notifyItemMoved(adapterPosition, notes.size - 1)
            } else {
                // Move the item to the top if unchecked
                val newList = mutableListOf<Note>().apply {
                    add(checkedNote)
                    addAll(notes.drop(1))
                }
                notes = newList
                notifyItemMoved(adapterPosition, 0)
            }

            // Update the item background based on the checkbox state
            val backgroundColor = if (isChecked) ContextCompat.getColor(holder.itemView.context, R.color.grey) else ContextCompat.getColor(holder.itemView.context, android.R.color.white)
            holder.linearLayout.setBackgroundColor(backgroundColor)
        }



        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context,UpdateActivity::class.java).apply {
                 putExtra("note_id",note.id)

            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteNote(note.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
        }

    }

    fun refreshData(newNotes:List<Note>){
        notes = newNotes
        notifyDataSetChanged()

    }
    private fun formatDeadline(deadline: Long): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = deadline
            dateFormatter.format(calendar.time)
        } catch (e: Exception) {
            ""
        }
    }

}