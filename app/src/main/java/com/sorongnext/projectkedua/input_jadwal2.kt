package com.sorongnext.projectkedua

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.widget.GridLayout
import android.widget.ImageView

class input_jadwal2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private var selectedTime: String? = null
    private var userName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_jadwal2)

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://sorongkicks-default-rtdb.firebaseio.com/")

        // Get references to UI components
        val lapanganName = findViewById<TextView>(R.id.lapangan2).text.toString() // Lapangan name
        val lanjutButton = findViewById<Button>(R.id.lanjut2)
        val timeGrid = findViewById<GridLayout>(R.id.btnjadwal)
        val menuIcon = findViewById<ImageView>(R.id.menuIcon2)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        // Set listener untuk menuIcon
        menuIcon.setOnClickListener {
            // Tampilkan atau sembunyikan tombol logout
            logoutBtn.visibility = if (logoutBtn.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        // Set listener untuk logoutBtn
        logoutBtn.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }

        val backBtn = findViewById<ImageButton>(R.id.backBtn)
        backBtn.setOnClickListener {
            val intent = Intent(this, input_sewa_lapangan::class.java)
            startActivity(intent)
            finish()
        }

        // Load booked times from Firebase
        database.reference.child("bookings").get().addOnSuccessListener { dataSnapshot ->
            val bookedTimes = mutableListOf<String>()
            for (child in dataSnapshot.children) {
                val time = child.child("time").getValue(String::class.java)
                val lapangan = child.child("lapangan").getValue(String::class.java)
                val status = child.child("status").getValue(String::class.java)

                // Check if the booking matches the current lapangan and status
                if (time != null && lapangan == lapanganName && status == "dibooking") {
                    bookedTimes.add(time)
                }
            }

            for (i in 0 until timeGrid.childCount) {
                val button = timeGrid.getChildAt(i) as Button
                if (bookedTimes.contains(button.text.toString())) {
                    // Set the button color dynamically based on lapanganName
                    val colorRes = when (lapanganName) {
                        "Lapangan 1" -> R.color.red
                        "Lapangan 2" -> R.color.red
                        "Lapangan 3" -> R.color.red
                        else -> R.color.green // Default color
                    }
                    button.setBackgroundTintList(resources.getColorStateList(colorRes, null))
                    button.isEnabled = false // Disable the button to prevent further selection
                }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Error loading bookings: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        // Set onClickListeners for time buttons
        for (i in 0 until timeGrid.childCount) {
            val button = timeGrid.getChildAt(i) as Button
            button.setOnClickListener {
                selectedTime = button.text.toString()
                Toast.makeText(this, "Waktu telah dipilih di jam: $selectedTime", Toast.LENGTH_SHORT).show()
            }
        }

        // Set onClickListener for "Lanjut" button
        lanjutButton.setOnClickListener {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                database.reference.child("users").child(userId).child("name").get()
                    .addOnSuccessListener { snapshot ->
                        userName = snapshot.getValue(String::class.java)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Gagal mengambil nama pengguna: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }

            if (userId == null) {
                Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedTime == null) {
                Toast.makeText(this, "Pilih jam main terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ambil nama pengguna sebelum menyimpan booking
            database.reference.child("users").child(userId).child("name").get()
                .addOnSuccessListener { snapshot ->
                    userName = snapshot.getValue(String::class.java)

                    if (userName == null) {
                        Toast.makeText(this, "Nama pengguna tidak ditemukan.", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    val bookingData = mapOf(
                        "userName" to userName,
                        "lapangan" to lapanganName,
                        "time" to selectedTime,
                        "userId" to userId,
                        "status" to "dibooking"
                    )

                    // Simpan data booking ke database
                    database.reference.child("bookings").push().setValue(bookingData)
                        .addOnSuccessListener {
                            // Update UI setelah berhasil menyimpan
                            for (i in 0 until timeGrid.childCount) {
                                val button = timeGrid.getChildAt(i) as Button
                                if (button.text.toString() == selectedTime) {
                                    button.setBackgroundTintList(resources.getColorStateList(R.color.red, null))
                                    button.isEnabled = false
                                    break
                                }
                            }
                            Toast.makeText(this, "Booking telah disimpan ;)", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error dalam menyimpan data booking: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal mengambil nama pengguna: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
