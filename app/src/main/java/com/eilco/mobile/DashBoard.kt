package com.eilco.mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class DashBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        // Récupération du bouton
        val addTaskButton: Button = findViewById(R.id.addTaskButton)

        // Gestion du clic sur le bouton
        addTaskButton.setOnClickListener {
            // Redirection vers AddTaskActivity
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }
    }
}