package com.eilco.mobile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent

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

        // Initialisation de FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Récupérer les références des vues
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        signUpTextView = findViewById(R.id.signUpTextView)

        // Gestion du bouton de connexion
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isValidEmail(email)) {
                    loginUser(email, password)
                } else {
                    Toast.makeText(this, "Veuillez entrer un email valide", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Veuillez entrer votre email et mot de passe", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirection vers la page d'inscription
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // Fonction de connexion de l'utilisateur
    private fun loginUser(email: String, password: String) {
        // Afficher un toast ou une ProgressBar pour signaler la connexion en cours
        Toast.makeText(this, "Connexion en cours...", Toast.LENGTH_SHORT).show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show()
                    // Rediriger vers une autre activité après la connexion réussie (par exemple, une page d'accueil)
                    val intent = Intent(this, DashBoard::class.java) // Créer la HomeActivity
                    startActivity(intent)
                    finish()  // Ferme la MainActivity pour éviter que l'utilisateur y revienne
                } else {
                    // Gérer l'échec de la connexion (mot de passe ou email incorrect)
                    Toast.makeText(this, "Échec de l'authentification : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Vérifier la validité de l'email
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
