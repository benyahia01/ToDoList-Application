package com.eilco.mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.eilco.mobile.module.Task
import com.google.firebase.firestore.FirebaseFirestore

class AddTask : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var saveTaskButton: Button
    private lateinit var cancelButton: Button

    private val db = FirebaseFirestore.getInstance()

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
                // Créer une nouvelle tâche
                val task = Task(title, description)

                // Ajouter la tâche dans Firestore
                db.collection("tasks")
                    .add(task)
                    .addOnSuccessListener {
                        // Tâche ajoutée avec succès
                        // Vous pouvez rediriger l'utilisateur ou afficher un message de succès
                    }
                    .addOnFailureListener { e ->
                        // En cas d'erreur
                    }
            }
        }

        // Annuler l'ajout de la tâche
        cancelButton.setOnClickListener {
            // Fermer l'activité ou réinitialiser les champs
            finish()
        }
    }
}
