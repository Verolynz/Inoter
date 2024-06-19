package com.verolynz.kelompok5.inoter.ui.adapters//package com.verolynz.kelompok5.inoter.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.data.local.AtletEntity


class AtletAdapter(private var atletList: List<AtletEntity>) :
    RecyclerView.Adapter<AtletAdapter.AtletViewHolder>() {

    // Deklarasi variabel untuk callback ketika item diklik
    private lateinit var onItemClickCallback: OnItemClickCallback

    // Fungsi untuk mengatur callback ketika item diklik
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Interface untuk callback ketika item diklik
    interface OnItemClickCallback {
        fun onItemClicked(data: AtletEntity)
        fun onDelete(data: AtletEntity, position: Int)
        fun onUpdate(data: AtletEntity)
    }

    // Kelas ViewHolder untuk menyimpan referensi view yang digunakan dalam RecyclerView
    class AtletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.namaatlet)
        val Deskripsi: TextView = itemView.findViewById(R.id.deskripsiatlet)

    }

    // Fungsi untuk membuat ViewHolder (Melakukan setting untuk XML yang akan kita gunakan untuk menampilkan data)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AtletViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.rvatlet, parent, false)
        return AtletViewHolder(view)
    }

    // Fungsi untuk mengikat data dengan ViewHolder (memasukkan data yang kita miliki ke dalam XML ViewHolder)
    override fun onBindViewHolder(holder: AtletViewHolder, position: Int) {
        val data = atletList[position]

        holder.Name.text = data.name
        holder.Deskripsi.text = data.deskripsi

        // Mengatur aksi ketika item diklik
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(atletList[holder.adapterPosition]) }

        // Mengatur aksi ketika button dihapus


        // Mengatur aksi ketika button diupdate


    }

    // Fungsi untuk mendapatkan jumlah item
    override fun getItemCount(): Int = atletList.size
}