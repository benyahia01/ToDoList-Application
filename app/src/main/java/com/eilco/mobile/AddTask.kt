package com.eilco.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eilco.mobile.module.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddTask : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveTaskButton: Button
    private lateinit var cancelButton: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        titleEditText = findViewById(R.id.taskTitleEditText)
        descriptionEditText = findViewById(R.id.taskDescriptionEditText)
        saveTaskButton = findViewById(R.id.saveTaskButton)
        cancelButton = findViewById(R.id.cancelButton)

        saveTaskButton.setOnClickListener {
            val title = titleEditText.text.toString().trim()
            val description = descriptionEditText.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(this, "Le titre est requis", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userEmail = auth.currentUser?.email
            if (userEmail == null) {
                Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val task = Task(title, description, userEmail)

            db.collection("tasks")
                .add(task)
                .addOnSuccessListener {
                    Toast.makeText(this, "Tâche ajoutée avec succès !", Toast.LENGTH_SHORT).show()

                    // 🔥 Au lieu d'aller vers TaskAddedActivity, on retourne à DashBoard 🔥
                    val intent = Intent(this, DashBoard::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    finish()  // Ferme cette activité
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erreur lors de l'ajout de la tâche: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }
}
