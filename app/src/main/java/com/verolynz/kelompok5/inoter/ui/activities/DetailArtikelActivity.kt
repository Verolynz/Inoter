package com.verolynz.kelompok5.inoter.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase
import java.io.ByteArrayOutputStream

class DetailArtikelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailartikel)

        // Mengambil data nama, deskripsi, dan gambar dari intent
        val getDataName = intent.getStringExtra("judul")
        val getDataDescription = intent.getStringExtra("deskripsi")
        val getDataImage =  intent.getIntExtra("image_artikel", 0)
//        // Mengambil data dari intent
//        val artikelData: ArtikelDatabase? = intent.getParcelableExtra("artikelData")


        // Menghubungkan variabel dengan komponen di layout
        val playerName = findViewById<TextView>(R.id.text_title)
        val playerDescription = findViewById<TextView>(R.id.text_content)
        val playerImage = findViewById<ImageView>(R.id.image_detail)



        // Menampilkan data pemain
        playerName.text = getDataName
        playerDescription.text = getDataDescription
        playerImage.setImageResource(getDataImage)

        // Mendapatkan referensi ke tombol bagikan
        val btnShare = findViewById<ImageButton>(R.id.bagikan_btn1)
//        val bitmap = BitmapFactory.decodeResource(resources, R.id.bagikan_btn1)

        val imageView: ImageView = findViewById(R.id.image_detail)

// Mendapatkan Drawable dari ImageView
        val drawable: Drawable? = imageView.drawable


        // Menetapkan aksi ketika tombol bagikan diklik
        btnShare.setOnClickListener {


            // Jika drawable tidak null, Anda dapat mengonversinya menjadi Bitmap
            if (drawable != null) {
                val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap

                // Sekarang Anda dapat menggunakan bitmap ini dalam Intent untuk dibagikan
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "$getDataName, $getDataDescription")
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
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }
}


