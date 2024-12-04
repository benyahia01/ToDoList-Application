package com.eilco.mobile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signUpTextView: TextView
    private lateinit var forgotPasswordTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation de Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Récupérer les vues
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpTextView = findViewById(R.id.signUpTextView)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)

        // Action lors du clic sur le bouton de connexion
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Veuillez entrer votre email et mot de passe", Toast.LENGTH_SHORT).show()
            }
        }

        // Action pour gérer l'inscription (facultatif)
        signUpTextView.setOnClickListener {
            // Rediriger vers l'écran d'inscription
            // Vous pouvez implémenter un Intent pour une activité d'inscription si nécessaire
            Toast.makeText(this, "Inscription", Toast.LENGTH_SHORT).show()
        }

        // Action pour gérer l'oubli du mot de passe (facultatif)
        forgotPasswordTextView.setOnClickListener {
            // Rediriger vers la réinitialisation du mot de passe
            Toast.makeText(this, "Réinitialisation du mot de passe", Toast.LENGTH_SHORT).show()
        }
    }

    // Fonction de connexion Firebase
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Connexion réussie, afficher un message de succès
                    Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show()
                    // Vous pouvez rediriger l'utilisateur vers une autre activité si nécessaire
                } else {
                    // En cas d'échec de la connexion, afficher un message d'erreur
                    Toast.makeText(this, "Échec de l'authentification", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
