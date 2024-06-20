package com.verolynz.kelompok5.inoter.ui.activities

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.room.AppViewModel
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase
import com.verolynz.kelompok5.inoter.room.RoomViewModelFactory
import com.verolynz.kelompok5.inoter.utils.reduceFileImage
import com.verolynz.kelompok5.inoter.utils.uriToFile
import java.io.File

class Updateartikel : AppCompatActivity() {
    // Mendeklarasikan variabel untuk menyimpan URI gambar yang dipilih
    private var currentImageUri: Uri? = null
    // Mendeklarasikan ImageView untuk menampilkan gambar yang dipilih
    private lateinit var artikelImage: ImageView
    // Mendeklarasikan ViewModel untuk interaksi dengan database
    private lateinit var appViewModel: AppViewModel
    // Mendeklarasikan EditText untuk input nama pemain
    private lateinit var artikelName: EditText
    // Mendeklarasikan EditText untuk input deskripsi pemain
    private lateinit var artikelDescription: EditText
    private var oldPhoto: File? = null

    private lateinit var getData: ArtikelDatabase




    // Mendeklarasikan image picker untuk memilih gambar dari galeri
    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            // Menampilkan ImageView jika gambar berhasil dipilih
            artikelImage.visibility = View.VISIBLE
            // Menyimpan URI gambar yang dipilih
            currentImageUri = firstImage.uri
            // Menampilkan pesan bahwa gambar berhasil dimasukkan
            //artikelImageInput.setText("Gambar berhasil dimasukkan")

            // Menggunakan library Glide untuk menampilkan gambar yang dipilih
            Glide.with(artikelImage)
                .load(firstImage.uri)
                .into(artikelImage)
        } else {
            // Menyembunyikan ImageView jika tidak ada gambar yang dipilih
            View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambahartikel)

        getData = intent.getParcelableExtra("player")!!

        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout
        artikelImage = findViewById(R.id.image_preview)
        Glide.with(this)
            .load(getData.image)
            .into(artikelImage)

        oldPhoto = getData.image

        artikelName = findViewById(R.id.edit_text_title)
        artikelName.setText(getData!!.name)
        artikelDescription = findViewById(R.id.edit_text_content)
        artikelDescription.setText(getData!!.description)


        // Memanggil fungsi onClick untuk menangani aksi klik
        onClick()
    }

    private fun onClick() {
        val btnSavedPlayer = findViewById<Button>(R.id.button_post)
        btnSavedPlayer.setOnClickListener {
            if (validateInput()) {
                savedData()
            }
        }


        // Menangani aksi klik pada EditText untuk memilih gambar
        val openImagePicker = findViewById<Button>(R.id.button_select_image)
        openImagePicker.setOnClickListener {
            imagePickerLauncher.launch(
                ImagePickerConfig {
                    mode = ImagePickerMode.SINGLE
                    returnMode = ReturnMode.ALL
                    isFolderMode = true
                    folderTitle = "Galeri"
                    isShowCamera = false
                    imageTitle = "Tekan untuk memilih gambar"
                    doneButtonText = "Selesai"
                }
            )
        }


    }

    // Fungsi untuk memvalidasi input
    private fun validateInput(): Boolean {
        var error = 0

        // Memeriksa apakah nama pemain kosong
        if (artikelName.text.toString().isEmpty()) {
            error++
            artikelName.error = "Nama Artikel tidak boleh kosong"
        }

        // Memeriksa apakah deskripsi pemain kosong
        if (artikelDescription.text.toString().isEmpty()) {
            error++
            artikelDescription.error = "Deskripsi Artikel tidak boleh kosong"
        }



        // Mengembalikan true jika tidak ada error, false jika ada error
        return error == 0
    }

    // Fungsi untuk menyimpan data pemain
    private fun savedData() {
        // Mengubah URI gambar menjadi file dan mengurangi ukuran file
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val artikel = (if (currentImageUri != null) imageFile else oldPhoto)?.let { imageFile ->
            ArtikelDatabase(
                id = getData.id,
                name = artikelName.text.toString(),
                description = artikelDescription.text.toString(),
                image = imageFile
            )
        }
        Log.d("artikel", artikel.toString())

        // Menyimpan data pemain ke database
        if (artikel != null) appViewModel.updateArtikel(artikel)

        // Menampilkan pesan bahwa data pemain berhasil ditambahkan
        Toast.makeText(
            this@Updateartikel,
            "Data Artikel berhasil diubah",
            Toast.LENGTH_SHORT
        ).show()

        // Mengakhiri activity
        finish()
    }
}