package com.eilco.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditTask : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var updateTaskButton: Button
    private lateinit var cancelButton: Button
    private val db = FirebaseFirestore.getInstance()
    private var taskId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        titleEditText = findViewById(R.id.taskTitleEditText)
        descriptionEditText = findViewById(R.id.taskDescriptionEditText)
        updateTaskButton = findViewById(R.id.updateTaskButton)
        cancelButton = findViewById(R.id.cancelButton)

        taskId = intent.getStringExtra("taskId")
        titleEditText.setText(intent.getStringExtra("taskTitle"))
        descriptionEditText.setText(intent.getStringExtra("taskDescription"))

        updateTaskButton.setOnClickListener {
            val newTitle = titleEditText.text.toString().trim()
            val newDescription = descriptionEditText.text.toString().trim()

            if (newTitle.isEmpty()) {
                Toast.makeText(this, "Le titre est requis", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            taskId?.let {
                db.collection("tasks").document(it)
                    .update("title", newTitle, "description", newDescription)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Tâche mise à jour !", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, DashBoard::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Erreur: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
