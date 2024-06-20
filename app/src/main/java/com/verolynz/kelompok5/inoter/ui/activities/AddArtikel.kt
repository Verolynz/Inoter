package com.verolynz.kelompok5.inoter.ui.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.verolynz.kelompok5.inoter.R
import com.verolynz.kelompok5.inoter.room.AppViewModel
import com.verolynz.kelompok5.inoter.room.ArtikelDatabase
import com.verolynz.kelompok5.inoter.room.RoomViewModelFactory
import com.verolynz.kelompok5.inoter.utils.reduceFileImage
import com.verolynz.kelompok5.inoter.utils.uriToFile

class AddArtikel : AppCompatActivity(){
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

        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout
        artikelImage = findViewById(R.id.image_preview)
        artikelName = findViewById(R.id.edit_text_title)
        artikelDescription = findViewById(R.id.edit_text_content)


        // Memanggil fungsi onClick untuk menangani aksi klik
        onClick()
    }

    private fun onClick() {
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

        // Menangani aksi klik pada tombol simpan
        val btnSavedPlayer = findViewById<Button>(R.id.button_post)
        btnSavedPlayer.setOnClickListener {
            // Memvalidasi input dan menyimpan data jika valid
            if (validateInput()) {
                savedData()
            }
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

        val artikel = imageFile?.let {
            ArtikelDatabase(
                id = 0,
                name = artikelName.text.toString(),
                description = artikelDescription.text.toString(),
                image = imageFile
            )
        }

        // Menyimpan data pemain ke database
        if (artikel != null) appViewModel.insertArtikel(artikel)

        // Menampilkan pesan bahwa data pemain berhasil ditambahkan
        Toast.makeText(
            this@AddArtikel,
            "Data Artikel berhasil ditambahkan",
            Toast.LENGTH_SHORT
        ).show()

        // Mengakhiri activity
        finish()
    }
}