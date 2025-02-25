package com.eilco.mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.eilco.mobile.module.Task
import com.google.firebase.firestore.FirebaseFirestore

class DashBoard : AppCompatActivity() {
    private lateinit var taskContainer: LinearLayout
    private lateinit var addTaskButton: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)

        taskContainer = findViewById(R.id.taskContainer)
        addTaskButton = findViewById(R.id.addTaskButton)

        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }

        loadTasksFromFirebase()
    }

    override fun onResume() {
        super.onResume()
        loadTasksFromFirebase()  // Recharger les tâches quand on revient sur l'écran
    }

    private fun loadTasksFromFirebase() {
        db.collection("tasks")
            .get()
            .addOnSuccessListener { result ->
                taskContainer.removeAllViews()
                for (document in result) {
                    val task = document.toObject(Task::class.java)
                    val taskId = document.id
                    addTaskToUI(task, taskId)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erreur: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun addTaskToUI(task: Task, taskId: String) {
        val taskLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.HORIZONTAL
            setPadding(0, 8, 0, 8)
        }

        val checkBox = CheckBox(this)
        val taskTextView = TextView(this).apply {
            text = "${task.title} - ${task.description}"
            textSize = 16f
            setPadding(8, 0, 0, 0)
        }

        val deleteButton = Button(this).apply {
            text = "🗑"
            textSize = 14f
            setOnClickListener { deleteTask(taskId) }
        }

        val editButton = Button(this).apply {
            text = "✏"
            textSize = 14f
            setOnClickListener { editTask(task, taskId) }
        }

        taskLayout.addView(checkBox)
        taskLayout.addView(taskTextView)
        taskLayout.addView(editButton)
        taskLayout.addView(deleteButton)
        taskContainer.addView(taskLayout)
    }

    private fun deleteTask(taskId: String) {
        db.collection("tasks").document(taskId)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Tâche supprimée", Toast.LENGTH_SHORT).show()
                loadTasksFromFirebase()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erreur de suppression: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun editTask(task: Task, taskId: String) {
        val intent = Intent(this, EditTask::class.java)
        intent.putExtra("taskId", taskId)
        intent.putExtra("taskTitle", task.title)
        intent.putExtra("taskDescription", task.description)
        startActivity(intent)
    }
}
