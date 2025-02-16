package com.eilco.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eilco.mobile.module.Task
import com.eilco.mobile.module.TaskAddedActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddTask : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveTaskButton: Button
    private lateinit var cancelButton: Button

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()  // Instance FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        titleEditText = findViewById(R.id.taskTitleEditText)
        descriptionEditText = findViewById(R.id.taskDescriptionEditText)
        saveTaskButton = findViewById(R.id.saveTaskButton)
        cancelButton = findViewById(R.id.cancelButton)

        // On click listener pour le bouton "Save Task"
        saveTaskButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()

            if (title.isNotEmpty()) {
                // Récupérer l'email de l'utilisateur connecté
                val userEmail = auth.currentUser?.email

                if (userEmail != null) {
                    // Créer une nouvelle tâche en incluant l'email
                    val task = Task(title, description, userEmail)

                    // Ajouter la tâche dans Firestore
                    db.collection("tasks")
                        .add(task)
                        .addOnSuccessListener {
                            // Tâche ajoutée avec succès
                            Toast.makeText(this, "Tâche ajoutée avec succès !", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, TaskAddedActivity::class.java)
                            startActivity(intent)
                            finish()  // Fermer cette activité
                        }
                        .addOnFailureListener { e ->
                            // En cas d'erreur
                            Toast.makeText(this, "Erreur lors de l'ajout de la tâche", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Si l'utilisateur n'est pas connecté ou n'a pas d'email
                    Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Si le titre est vide
                Toast.makeText(this, "Le titre est requis", Toast.LENGTH_SHORT).show()
            }
        }

        // Annuler l'ajout de la tâche
        cancelButton.setOnClickListener {
            // Fermer l'activité ou réinitialiser les champs
            finish()
        }
    }
}
