package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.room.AppViewModel
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase
import com.verolynz.kelompok5.inoter.room.RoomViewModelFactory
import com.verolynz.kelompok5.inoter.ui.adapters.ArtikelAdapterAddRoom
import com.verolynz.kelompok5.inoter.ui.adapters.ArtikelAdapterRoom

class AdminMainActivity : AppCompatActivity() {
    private lateinit var appViewModel: AppViewModel
    private lateinit var artikelAdapterRoom: ArtikelAdapterRoom
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]
        
        // Menghubungkan variabel dengan komponen di layout
        recyclerView = findViewById(R.id.rvartikel)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

//        recyclerviewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        recyclerviewHorizontal.adapter = Adapter

        // Mengamati perubahan data pemain dan memperbarui RecyclerView
        appViewModel.getAllArtikel().observe(this) { artikelData ->
            if (artikelData != null) {
                artikelAdapterRoom = ArtikelAdapterRoom(artikelData)
                recyclerView.adapter = artikelAdapterRoom

                /// Menangani aksi klik pada item di RecyclerView
                artikelAdapterRoom.setOnItemClickCallback(object :
                    ArtikelAdapterRoom.OnItemClickCallback {
                    override fun onItemClicked(data: ArtikelDatabase) {
                        showSelectedArtikel(data)
                    }
                    override fun onMoreClicked(data: ArtikelDatabase, position: Int) {
                        PopUpArtikel(data, position).show(supportFragmentManager, PopUpArtikel.TAG)


                    }
                })
            }
        }
        // Menangani aksi klik pada tombol tambah pemain
        val lihatArtikel = findViewById<TextView>(R.id.lihatartikel)
        lihatArtikel.setOnClickListener {
            val intent = Intent(this, SeeMoreArtkelActivity::class.java)
            startActivity(intent)
        }

    }
    private fun showSelectedArtikel(data: ArtikelDatabase) {
        // Membuat intent untuk berpindah ke DetailPlayerActivity
        val navigateToDetail = Intent(this, DetailArtikelActivity::class.java)

        // Menambahkan dan membawa data pemain ke intent dengan tujuan ke DetailPlayerActivity
        navigateToDetail.putExtra("judul", data.name)
        navigateToDetail.putExtra("deskripsi", data.description)
        navigateToDetail.putExtra("artikel_image", data.image)

        // Memulai activity baru
        startActivity(navigateToDetail)
    }





}
