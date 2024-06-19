package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.data.viewmodels.OlahragaViewModels
import com.verolynz.kelompok5.inoter.data.viewmodels.ViewModelFactory
import com.verolynz.kelompok5.inoter.room.AppViewModel
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase
import com.verolynz.kelompok5.inoter.room.RoomViewModelFactory
import com.verolynz.kelompok5.inoter.ui.adapters.ArtikelAdapterRoom
import java.io.File

class AdminMainActivity : AppCompatActivity() {
    private lateinit var appViewModel: AppViewModel
    private lateinit var artikelAdapterRoom: ArtikelAdapterRoom
    private lateinit var recyclerView: RecyclerView
    private lateinit var olahragaViewModel: OlahragaViewModels
//    private lateinit var olahragaAdapter: OlahragaAdapter
    private lateinit var recyclerViewOlahraga: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        val olahragafactory = ViewModelFactory.getInstance(this)
        val olahragaViewModel = ViewModelProvider(this, olahragafactory)[OlahragaViewModels::class.java]

        // Menghubungkan variabel dengan komponen di layout
        recyclerView = findViewById(R.id.rvartikel)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewOlahraga = findViewById(R.id.rvolahraga)
        recyclerViewOlahraga.layoutManager = GridLayoutManager(this, 4)

        // Mengamati perubahan data artikel dan memperbarui RecyclerView
        appViewModel.getAllArtikel().observe(this) { artikelData ->
            if (artikelData != null) {
                artikelAdapterRoom = ArtikelAdapterRoom(artikelData)
                recyclerView.adapter = artikelAdapterRoom

                // Menangani aksi klik pada item di RecyclerView
                artikelAdapterRoom.setOnItemClickCallback(object : ArtikelAdapterRoom.OnItemClickCallback {
                    override fun onItemClicked(data: ArtikelDatabase) {
                        showSelectedArtikel(data)
                    }
                    override fun onMoreClicked(data: ArtikelDatabase, position: Int) {
                        PopUpArtikel(data, position).show(supportFragmentManager, PopUpArtikel.TAG)
                    }
                })
            }
        }

        // Menangani aksi klik pada tombol lihat artikel
        val lihatArtikel = findViewById<TextView>(R.id.lihatartikel)
        lihatArtikel.setOnClickListener {
            val intent = Intent(this, SeeMoreArtkelActivity::class.java)
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
