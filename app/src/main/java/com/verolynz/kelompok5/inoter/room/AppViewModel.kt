package com.verolynz.kelompok5.inoter.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class AppViewModel(private val appRepository: AppRepository) : ViewModel() {

    // Memasukkan data pemain ke dalam database
    fun insertArtikel(artikel: ArtikelDatabase) {
        appRepository.insertArtikel(artikel)
    }

    // Mendapatkan semua data pemain dari database
    fun getAllArtikel(): LiveData<List<ArtikelDatabase>> {
        return appRepository.getAllArtikel()
    }

    fun updateArtikel(artikel: ArtikelDatabase) {
        appRepository.updateArtikel(artikel)
    }
    fun deleteArtikel(artikel: ArtikelDatabase) {
        appRepository.deleteArtikel(artikel)
    }



}