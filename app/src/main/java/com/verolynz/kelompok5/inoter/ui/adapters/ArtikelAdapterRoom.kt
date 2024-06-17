package com.verolynz.kelompok5.inoter.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase

class ArtikelAdapterRoom(private var artikelList: List<ArtikelDatabase>) :
    RecyclerView.Adapter<ArtikelAdapterRoom.ArtikelViewHolder>() {

    // Deklarasi variabel untuk callback ketika item diklik
    private lateinit var onItemClickCallback: OnItemClickCallback

    // Fungsi untuk mengatur callback ketika item diklik
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Interface untuk callback ketika item diklik
    interface OnItemClickCallback {
        fun onItemClicked(data: ArtikelDatabase)
    }

    // Kelas ViewHolder untuk menyimpan referensi view yang digunakan dalam RecyclerView
    class ArtikelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val artikelName: TextView = itemView.findViewById(R.id.textViewTitle)
        val artikelDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val artikelImage: ImageView = itemView.findViewById(R.id.imageViewArtikel)
    }

    // Fungsi untuk membuat ViewHolder (Melakukan setting untuk XML yang akan kita gunakan untuk menampilkan data)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtikelViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.rvartikelmainactivity, parent, false)
        return ArtikelViewHolder(view)
    }

    // Fungsi untuk mengikat data dengan ViewHolder (memasukkan data yang kita miliki ke dalam XML ViewHolder)
    override fun onBindViewHolder(holder: ArtikelViewHolder, position: Int) {
        val data = artikelList[position]

        holder.artikelName.text = data.name
        holder.artikelDescription.text = data.description.shorten(85)

        // Mengatur image
        val uri = Uri.fromFile(data.image)
        holder.artikelImage.setImageURI(uri)

        // Mengatur aksi ketika item diklik
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(artikelList[holder.adapterPosition])
        }
    }

    // Fungsi untuk mendapatkan jumlah item
    override fun getItemCount(): Int = artikelList.size

    // Fungsi untuk memendekkan teks jika melebihi panjang maksimum
    private fun String.shorten(maxLength: Int): String {
        return if (this.length <= maxLength) this else "${this.substring(0, maxLength)}..."
    }
}
