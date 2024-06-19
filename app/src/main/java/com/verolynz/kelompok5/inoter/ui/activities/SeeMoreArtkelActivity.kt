package com.verolynz.kelompok5.inoter.ui.activities

import ArtikelAdapterAddRoom
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.room.AppViewModel
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase
import com.verolynz.kelompok5.inoter.room.RoomViewModelFactory
import java.io.File

class SeeMoreArtkelActivity : AppCompatActivity() {
    private lateinit var appViewModel: AppViewModel
    private lateinit var artikelAdapterRoom: ArtikelAdapterAddRoom
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.artikellinearactivity)

        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout
        recyclerView = findViewById(R.id.recycler_view_linear)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengamati perubahan data pemain dan memperbarui RecyclerView
        appViewModel.getAllArtikel().observe(this) { artikelData ->
            if (artikelData != null) {
                artikelAdapterRoom = ArtikelAdapterAddRoom(artikelData)
                recyclerView.adapter = artikelAdapterRoom

                // Menangani aksi klik pada item di RecyclerView
                artikelAdapterRoom.setOnItemClickCallback(object : ArtikelAdapterAddRoom.OnItemClickCallback {
                    override fun onItemClicked(data: ArtikelDatabase) {
                        showSelectedArtikel(data)
                    }
                    override fun onMoreClicked(data: ArtikelDatabase, position: Int) {
                        PopUpArtikel(data, position).show(supportFragmentManager, PopUpArtikel.TAG)
                    }
                })
            }
        }

        // Menangani aksi klik pada tombol tambah artikel
        val tambahArtikel = findViewById<FloatingActionButton>(R.id.fab_add_article)
        tambahArtikel.setOnClickListener {
            val intent = Intent(this, AddArtikel::class.java)
            startActivity(intent)
        }
    }

    private fun showSelectedArtikel(data: ArtikelDatabase) {
        val navigateToDetail = Intent(this, DetailArtikelActivity::class.java)

        navigateToDetail.putExtra("judul", data.name)
        navigateToDetail.putExtra("deskripsi", data.description)

        // Mengonversi data.image ke String URI
        val imageUriString = if (data.image is File) {
            Uri.fromFile(data.image).toString()
        } else {
            data.image.toString() // Jika sudah merupakan Uri dalam bentuk String
        }

        navigateToDetail.putExtra("artikel_image", imageUriString)

        startActivity(navigateToDetail)
    }
}
