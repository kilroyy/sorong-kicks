package com.sorongnext.projectkedua.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sorongnext.projectkedua.Model.Booking
import com.sorongnext.projectkedua.R

class BookingAdapter(private val bookingList: List<Booking>) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return BookingViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val booking = bookingList[position]
        holder.bind(booking)
    }

    override fun getItemCount(): Int = bookingList.size

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textNama: TextView = view.findViewById(R.id.textNama)
        private val textLapangan: TextView = view.findViewById(R.id.textLapangan)
        private val textJam: TextView = view.findViewById(R.id.textJam)
        private val imageLapangan: ImageView = view.findViewById(R.id.imageLapangan)

        fun bind(booking: Booking) {
            textNama.text = "Nama: ${booking.userName}"
            textLapangan.text = "Lapangan: ${booking.lapangan}"
            textJam.text = "Jam: ${booking.time}"
            // Load gambar (opsional, jika tersedia)
            Glide.with(imageLapangan.context)
                .load("URL_GAMBAR") // Ganti dengan URL yang sesuai
                .placeholder(R.drawable.lapangan1) // Placeholder jika gambar belum ada
                .into(imageLapangan)
        }
    }
}
