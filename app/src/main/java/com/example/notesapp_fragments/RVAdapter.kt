package com.example.notesapp_fragments

import android.graphics.Color
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RVAdapter(private val Fragment: MainFragment): RecyclerView.Adapter<RVAdapter.MessageViewHolder>() {
    private var Notes: List<Notes> = listOf()

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVAdapter.MessageViewHolder {
        return RVAdapter.MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RVAdapter.MessageViewHolder, position: Int) {
        val Note = Notes[position]

        holder.itemView.apply {
            tvNote.text = Note.note

            if(position%2==0){
                llitem.setBackgroundColor(Color.parseColor("#A5D1D6"))
            }else{
                llitem.setBackgroundColor(Color.parseColor("#40A09F9F"))
            }

            EditNote.setOnClickListener {
                Fragment.update(Note.id)
            }

            DeleteNote.setOnClickListener {
                Fragment.deleteNote(Note.id)
            }
        }
    }

    override fun getItemCount() = Notes.size

    fun setNotes(Notes: List<Notes>){
        this.Notes = Notes
        notifyDataSetChanged()


    }
}