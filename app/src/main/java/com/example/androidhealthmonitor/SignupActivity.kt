package com.example.androidhealthmonitor


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.androidhealthmonitor.util.SessionManager


class SignupActivity : AppCompatActivity() {
    private lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        session = SessionManager(this)


        val etUser = findViewById<EditText>(R.id.etUser)
        val etPass = findViewById<EditText>(R.id.etPass)
        val btnCreate = findViewById<Button>(R.id.btnCreate)


        btnCreate.setOnClickListener {
            val u = etUser.text.toString().trim()
            val p = etPass.text.toString().trim()
            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Enter values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            session.saveUser(u, p)
            Toast.makeText(this, "User saved. Login now.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}