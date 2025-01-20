package com.sorongnext.projectkedua

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sorongnext.projectkedua.Adapter.BookingAdapter
import com.sorongnext.projectkedua.Model.Booking

class jadwal_main : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookingAdapter: BookingAdapter
    private val bookingList = mutableListOf<Booking>()
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jadwal_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerViewJadwal)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookingAdapter = BookingAdapter(bookingList)
        recyclerView.adapter = bookingAdapter

        database = FirebaseDatabase.getInstance().getReference("bookings")
        fetchData()
    }

    private fun fetchData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                bookingList.clear()
                for (data in snapshot.children) {
                    val booking = data.getValue(Booking::class.java)
                    if (booking != null) {
                        bookingList.add(booking)
                    }
                }
                bookingAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        val menuIcon = findViewById<ImageView>(R.id.menuIcon4)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

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

        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }
}