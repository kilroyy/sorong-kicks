package com.sorongnext.projectkedua

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val menuIcon = findViewById<ImageView>(R.id.menuIcon)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        val leftCard = findViewById<CardView>(R.id.leftCard)
        leftCard.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, input_sewa_lapangan::class.java)
            startActivity(intent)
        }

        val rigthCard = findViewById<CardView>(R.id.rightCard)
        rigthCard.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, jadwal_main::class.java)
            startActivity(intent)
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