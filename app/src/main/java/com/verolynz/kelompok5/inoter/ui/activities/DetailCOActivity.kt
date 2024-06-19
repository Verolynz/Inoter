package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.verolynz.kelompok5.inoter.R

class DetailCOActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailolahraga)

        val getDataName = intent.getStringExtra("name")
        val getDataDescription = intent.getStringExtra("deskripsi")


        val Name = findViewById<TextView>(R.id.olahraga_judul)
        val Description = findViewById<TextView>(R.id.olahraga_deskripsi)


        Name.text = getDataName
        Description.text = getDataDescription


        // Mendapatkan referensi ke tombol bagikan
        val btnShare = findViewById<ImageButton>(R.id.bagikan_btn1)

        // Menetapkan aksi ketika tombol bagikan diklik

    }

}