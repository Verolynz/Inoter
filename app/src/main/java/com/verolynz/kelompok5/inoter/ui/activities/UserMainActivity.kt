package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.data.local.COEntity
import com.verolynz.kelompok5.inoter.data.local.OlahragaDB
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository
import com.verolynz.kelompok5.inoter.data.viewmodels.OlahragaViewModels
import com.verolynz.kelompok5.inoter.data.viewmodels.ViewModelFactory
import com.verolynz.kelompok5.inoter.room.AppViewModel
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase
import com.verolynz.kelompok5.inoter.room.RoomViewModelFactory
import com.verolynz.kelompok5.inoter.ui.adapters.ArtikelAdapterRoom
import com.verolynz.kelompok5.inoter.ui.adapters.COAdapter
import java.io.File
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils
import java.time.LocalTime

class UserMainActivity : AppCompatActivity() {
    private lateinit var appViewModel: AppViewModel
    private lateinit var artikelAdapterRoom: ArtikelAdapterRoom
    private lateinit var recyclerView: RecyclerView
    private lateinit var olahragaViewModel: OlahragaViewModels
    private lateinit var recyclerViewOlahraga: RecyclerView
    private lateinit var olaharagaRepository: OlahragaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainuser)
        val jambukaapk = LocalTime.now()
        val earlyMorning = LocalTime.of(5, 0)
        val noon = LocalTime.of(12, 0)
        val evening = LocalTime.of(18, 0)
        val night = LocalTime.of(23, 59) // Representing midnight

        val greetingtext = findViewById<TextView>(R.id.salam)
        val sun = findViewById<ImageView>(R.id.sun)

        when {
            jambukaapk.isBefore(earlyMorning) -> {
                sun.setImageResource(0)
                val username = intent.getStringExtra("username")
                greetingtext.text = "Selamat Subuh, $username"
            }
            jambukaapk.isBefore(noon) -> {
                val username = intent.getStringExtra("username")
                greetingtext.text = "Selamat Pagi, $username"
            }
            jambukaapk.isBefore(evening) -> {
                val username = intent.getStringExtra("username")
                greetingtext.text = "Selamat Siang, $username"
            }
            jambukaapk.isBefore(night) -> {
                val username = intent.getStringExtra("username")
                sun.setImageResource(R.drawable.baseline_nights_stay_24)
                greetingtext.text = "Selamat Malam, $username "
            }
            else -> {
                val username = intent.getStringExtra("username")
                greetingtext.text = "Selamat Malam, $username"
            }
        }
        val olahragafactory = ViewModelFactory.getInstance(this)
        val olahragaViewModel = ViewModelProvider(this, olahragafactory)[OlahragaViewModels::class.java]
        val cODao = OlahragaDB.getDatabase(this).CODao()
        val atletDao = OlahragaDB.getDatabase(this).AtletDao()
        val usersDao = OlahragaDB.getDatabase(this).UsersDao()
        val executorsUtils = ExecutorsUtils()
        olaharagaRepository = OlahragaRepository.getInstance(cODao, atletDao, usersDao, executorsUtils)
        olaharagaRepository.getAllCO()
        olaharagaRepository.getAllAtlet()
        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]



        // Menghubungkan variabel dengan komponen di layout
        recyclerView = findViewById(R.id.rvartikel)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewOlahraga = findViewById(R.id.rvolahraga)
        recyclerViewOlahraga.layoutManager = GridLayoutManager(this, 2)

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




        olahragaViewModel.getAllCOlist().observe(this) { listCO ->
            if (listCO != null) {
                val coAdapter = COAdapter(listCO)
                recyclerViewOlahraga.adapter = coAdapter
                for (item in listCO) {
                    Log.d("AdminMainActivity", "CO item: $item")
                }
                coAdapter.setOnItemClickCallback(object : COAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: COEntity) {
                        showSelectedOlahraga(data)
                    }

                    override fun onDelete(data: COEntity, position: Int) {
//
                    }

                    override fun onUpdate(data: COEntity) {
//
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
        navigateToDetail.putExtra("id", data.id)
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

    private fun showSelectedOlahraga(data: COEntity) {
        val navigateToDetail = Intent(this, DetailCOActivity::class.java)
        navigateToDetail.putExtra("id", data.id)
        navigateToDetail.putExtra("name", data.name)
        navigateToDetail.putExtra("deskripsi", data.deskripsi)

        startActivity(navigateToDetail)

    }

}
