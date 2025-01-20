package com.sorongnext.projectkedua

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.widget.Toast
import android.widget.EditText




class login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Get UI references
        val emailField = findViewById<EditText>(R.id.email)
        val passwordField = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginbtn)
        val registerTextView = findViewById<TextView>(R.id.register)

        // Handle login button click
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Validate input fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Isi Nama dan Email terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Log in the user with Firebase Authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login successful
                        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()

                        // Redirect to the main/home activity
                        val intent = Intent(this, Dashboard::class.java) // Replace MainActivity with your target activity
                        startActivity(intent)
                        finish()
                    } else {
                        // Login failed
                        Toast.makeText(this, "Login Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Handle register text click
        registerTextView.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, registrasi::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}