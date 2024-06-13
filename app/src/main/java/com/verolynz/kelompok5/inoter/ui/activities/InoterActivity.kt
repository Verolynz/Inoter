package com.verolynz.kelompok5.inoter.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.verolynz.kelompok5.inoter.R
//Untuk mencapai ini, Anda perlu membuat fungsi yang memeriksa koneksi internet dan berdasarkan status koneksi, memutuskan dari mana data harus dimuat. Anda juga perlu membuat fungsi yang memperbarui database setiap kali aplikasi terhubung ke internet

class InoterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}