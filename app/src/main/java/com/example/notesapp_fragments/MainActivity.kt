package com.example.notesapp_fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
  //  private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        navController = Navigation.findNavController(this, R.id.my_nav)

    }

    /*override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }*/
}