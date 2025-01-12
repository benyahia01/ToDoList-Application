package com.eilco.mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Délai de 2 secondes avant de passer à la page de login
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java) // Redirige vers MainActivity (login)
            startActivity(intent)
            finish() // Ferme l'écran splash après la redirection
        }, 2000) // 2 secondes d'attente
    }
}

