package com.example.notesapp_fragments


import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val NoteModel by lazy { ViewModelProvider(this).get(NoteViewModel::class.java) }

    private lateinit var RC: RecyclerView
    private lateinit var RCadapter: RVAdapter
    private lateinit var ed: EditText
    private lateinit var btn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        NoteModel.getNotes().observe(viewLifecycleOwner, { notes ->
            RCadapter.setNotes(notes)

        })
        CoroutineScope(IO).launch {
            delay(1000)

            NoteModel.getAllNots()
        }


        ed = view.findViewById(R.id.ed)
        btn = view.findViewById(R.id.btn)

        RC = view.findViewById(R.id.rv)
        RCadapter = RVAdapter(this)
        RC.adapter = RCadapter

       // RC.layoutManager = GridLayoutManager(requireContext(),2)
        RC.layoutManager = LinearLayoutManager(requireContext())

        btn.setOnClickListener {
            val note1 = ed.text.toString()
            if (note1.isNotEmpty()) {
                val n = Notes("", note1)
                addNote(n)
            }
        }

        return view
    }

    fun addNote(note: Notes){
        NoteModel.addNote(note)
        Toast.makeText(requireContext(), "data saved successfully!", Toast.LENGTH_SHORT).show()
        ed.text.clear()
        ed.clearFocus()
    }

    fun deleteNote( noteId : String ) {

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("yes", DialogInterface.OnClickListener {

                    dialog, id -> NoteModel.deleteNote( noteId )

            })
            .setNegativeButton("No", DialogInterface.OnClickListener {

                    dialog, id -> dialog.cancel()

            })

        val alert = dialogBuilder.create()
        alert.setTitle("Are you sure want to delete?")
        alert.show()

    }

    fun update(id : String){
        val bundle = Bundle()
        bundle.putString("NoteId",id )
        val fragment= UpdateFragment()
        fragment.arguments=bundle
        fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()

    }
    }
