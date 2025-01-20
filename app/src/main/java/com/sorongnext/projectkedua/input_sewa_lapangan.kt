package com.sorongnext.projectkedua

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class input_sewa_lapangan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_sewa_lapangan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val menuIcon = findViewById<ImageView>(R.id.menuIcon)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)


        val buttonBooking1 = findViewById<Button>(R.id.buttonBooking1)
        buttonBooking1.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, input_jadwal::class.java)
            startActivity(intent)
        }

        val buttonBooking2 = findViewById<Button>(R.id.buttonBooking2)
        buttonBooking2.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, input_jadwal2::class.java)
            startActivity(intent)
        }

        val buttonBooking3 = findViewById<Button>(R.id.buttonBooking3)
        buttonBooking3.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, input_jadwal3::class.java)
            startActivity(intent)
        }

        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener {
            // Arahkan ke halaman dashboard
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            finish() // Tutup Activity ini agar tidak dapat kembali ke sini dengan tombol back
        }


        // Set listener untuk menuIcon
        menuIcon.setOnClickListener {
            // Tampilkan atau sembunyikan tombol logout
            if (logoutBtn.visibility == View.GONE) {
                logoutBtn.visibility = View.VISIBLE
            } else {
                logoutBtn.visibility = View.GONE
            }
        }

        // Set listener untuk logoutBtn
        logoutBtn.setOnClickListener {
            // Arahkan ke halaman login
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish() // Hentikan Activity ini agar tidak kembali ke sini
        }
    }
}