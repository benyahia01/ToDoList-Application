package com.eilco.mobile.module
import com.eilco.mobile.R



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.eilco.mobile.DashBoard

class TaskAddedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_added)

        val backToDashboardButton: Button = findViewById(R.id.backToDashboardButton)

        backToDashboardButton.setOnClickListener {
            val intent = Intent(this, DashBoard::class.java)
            startActivity(intent)
            finish()
        }
    }
}
