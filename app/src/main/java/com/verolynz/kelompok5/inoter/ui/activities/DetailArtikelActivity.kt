package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.verolynz.kelompok5.inoter.R
import java.io.ByteArrayOutputStream

class DetailArtikelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailartikel)

        val getDataName = intent.getStringExtra("judul")
        val getDataDescription = intent.getStringExtra("deskripsi")
        val getDataImageUriString = intent.getStringExtra("artikel_image") // Mengambil URI sebagai String

        val playerName = findViewById<TextView>(R.id.text_title)
        val playerDescription = findViewById<TextView>(R.id.text_content)
        val playerImage = findViewById<ImageView>(R.id.image_detail)

        playerName.text = getDataName
        playerDescription.text = getDataDescription

        if (getDataImageUriString != null) {
            val getDataImageUri = Uri.parse(getDataImageUriString) // Mengonversi kembali ke Uri
            // Menampilkan gambar dari URI menggunakan Glide
            Glide.with(this)
                .load(getDataImageUri)
                .placeholder(R.drawable.ic_launcher_background) // Gambar placeholder jika gambar belum dimuat
                .error(R.drawable.sign) // Gambar error jika terjadi kesalahan saat memuat gambar
                .into(playerImage)
        }

        // Mendapatkan referensi ke tombol bagikan
        val btnShare = findViewById<ImageButton>(R.id.bagikan_btn1)

        // Menetapkan aksi ketika tombol bagikan diklik
        btnShare.setOnClickListener {
            // Mendapatkan Drawable dari ImageView
            val drawable: Drawable? = playerImage.drawable

            // Jika drawable tidak null, Anda dapat mengonversinya menjadi Bitmap
            if (drawable != null) {
                val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap
                val message = "Saya melihat artikel ini di aplikasi Inoter:\n\n" +
                        "Judul Artikel: $getDataName\n" +
                        "Isi: $getDataDescription" +
                        "*UNDUH APIKASI INOTER DI LAMAN RESMI : INOTER.COM/APP*"

                // Sekarang Anda dapat menggunakan bitmap ini dalam Intent untuk dibagikan
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, message)
                    type = "text/plain"
                    putExtra(Intent.EXTRA_STREAM, getImageUri(this@DetailArtikelActivity, bitmap))
                    type = "image/*"
                }

                // Memulai aktivitas untuk memilih aplikasi untuk berbagi
                startActivity(Intent.createChooser(sendIntent, "Bagikan Konten Menggunakan:"))
            } else {
                // Handle jika drawable null
                Toast.makeText(this, "Gambar tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getImageUri(context: Context, bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }
}
