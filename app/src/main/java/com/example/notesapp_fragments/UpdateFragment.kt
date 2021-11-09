package com.example.notesapp_fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider


class UpdateFragment : Fragment() {

    private val NoteModel by lazy { ViewModelProvider(this).get( NoteViewModel ::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        val etNote = view.findViewById<EditText>(R.id.etUpdate)
        val btUpdate = view.findViewById<Button>(R.id.btUpdate)
        btUpdate.setOnClickListener {
            val args =this.arguments
            val noteId = args?.get("NoteId").toString()
            val newNote = etNote.text.toString()

            NoteModel.updateNote(noteId, newNote)
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,MainFragment())?.commit()

        }

        return view
    }


}