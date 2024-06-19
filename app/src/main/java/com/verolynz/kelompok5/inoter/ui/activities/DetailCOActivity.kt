package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton

import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.verolynz.kelompok5.inoter.R

import com.verolynz.kelompok5.inoter.data.local.OlahragaDB
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository
import com.verolynz.kelompok5.inoter.data.viewmodels.OlahragaViewModels
import com.verolynz.kelompok5.inoter.data.viewmodels.ViewModelFactory
import com.verolynz.kelompok5.inoter.ui.adapters.AtletAdapter
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils

class DetailCOActivity : AppCompatActivity() {
    private lateinit var olahragaVM: OlahragaViewModels
    private lateinit var recyclerViewAtlet: RecyclerView
    private lateinit var olaharagaRepository: OlahragaRepository
    private lateinit var atletadapter: AtletAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailolahraga)
        val olahragafactory = ViewModelFactory.getInstance(this)
        val olahragaVM = ViewModelProvider(this, olahragafactory)[OlahragaViewModels::class.java]
        val cODao = OlahragaDB.getDatabase(this).CODao()
        val atletDao = OlahragaDB.getDatabase(this).AtletDao()
        val usersDao = OlahragaDB.getDatabase(this).UsersDao()
        val executorsUtils = ExecutorsUtils()
        olaharagaRepository = OlahragaRepository.getInstance(cODao, atletDao, usersDao, executorsUtils)
        val getIdolaharaga = intent.getIntExtra("id", 0)
        val getDataName = intent.getStringExtra("name")
        val getDataDescription = intent.getStringExtra("deskripsi")

        olaharagaRepository.getAtletbyidCO(id = getIdolaharaga)

        recyclerViewAtlet = findViewById(R.id.rvatletcuy)
        recyclerViewAtlet.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val Name = findViewById<TextView>(R.id.olahraga_judul)
        val Description = findViewById<TextView>(R.id.olahraga_deskripsi)
        val back = findViewById<ImageButton>(R.id.back_button)


        Name.text = getDataName
        Description.text = getDataDescription

        olahragaVM.getAtletbyidCO(id = getIdolaharaga).observe(this) { atlet ->
            if (atlet != null) {
                for (item in atlet) {
                    Log.d("DetailCOActivity", "Atlet item: $item")
                }
                atletadapter = AtletAdapter(atlet)
                recyclerViewAtlet.adapter = atletadapter



            } else {
                Log.d("DetailCOActivity", "Atlet data is null")
            }
            Log.d("DetailCOActivity", "Atlet data: $atlet")

        }


        back.setOnClickListener {
            val intent = Intent(this, AdminMainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Menetapkan aksi ketika tombol bagikan diklik

    }

}