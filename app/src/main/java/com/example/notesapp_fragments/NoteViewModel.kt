package com.example.notesapp_fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NoteViewModel(Context: Application) : AndroidViewModel(Context) {

    val db = Firebase.firestore
    private val notes : MutableLiveData<List<Notes>>

    init {

        notes = MutableLiveData()
    }

    fun getNotes(): LiveData<List<Notes>> {
        return notes
    }


    fun getAllNots(){

            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->

                    val AllNotes = arrayListOf<Notes>()
                    for (document in result) {

                        document.data.map { (key, value) -> AllNotes.add(Notes(document.id, value.toString())) }
                    }
                    notes.postValue(AllNotes)
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }


    }

    fun addNote(note: Notes){

            val NewNote = hashMapOf(

                "note" to note.note
            )
            db.collection("notes").add(NewNote)
            getAllNots()

    }

    fun updateNote(id: String, note: String){

        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    if(document.id == id){
                        db.collection("notes").document(id).update("note", note)
                    }
                }

                getAllNots()
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }

    }

    fun deleteNote(id: String){

        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if(document.id == id){
                        db.collection("notes").document(id).delete()
                    }
                }
                getAllNots()
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }

    }


}