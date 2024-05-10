package com.example.structured
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes:List<Note>,context :Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val db : NotedatabaseHelper = NotedatabaseHelper(context)

    class NoteViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val titletextView : TextView = itemView.findViewById(R.id.titletext)
        val contenttextView : TextView = itemView.findViewById(R.id.contenttextview)
        val updateButton : ImageButton = itemView.findViewById(R.id.updatebtn)
        val deleteButton : ImageButton = itemView.findViewById(R.id.deletebtn)
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

}