package com.verolynz.kelompok5.inoter.room

import androidx.lifecycle.LiveData
import com.bumptech.glide.util.Executors
import com.verolynz.kelompok5.inoter.utils.ExecutorsUtils

class AppRepository private constructor(private val appDao: AppDao, private val ExecutorsUtils: ExecutorsUtils) {

    // Mendapatkan semua data pemain dari database
    fun getAllArtikel(): LiveData<List<ArtikelDatabase>> = appDao.getAllArtikel()

    // Memasukkan data pemain ke dalam database
    fun insertArtikel(artikel: ArtikelDatabase) {
        // Menjalankan operasi insert di thread yang berbeda
        ExecutorsUtils.diskIO().execute { appDao.insertArtikel(artikel) }
    }

    // Memperbarui data pemain ke dalam database
    fun updateArtikel(artikel: ArtikelDatabase) {
        // Menjalankan operasi insert di thread yang berbeda
        ExecutorsUtils.diskIO().execute { appDao.updateArtikel(artikel) }
    }
    // Menghapus data pemain ke dalam database
    fun deleteArtikel(artikel: ArtikelDatabase) {
        // Menjalankan operasi insert di thread yang berbeda
        ExecutorsUtils.diskIO().execute { appDao.deleteArtikel(artikel) }
    }



    companion object {
        // Variabel untuk menyimpan instance dari AppRepository
        @Volatile
        private var instance: AppRepository? = null

        // Mendapatkan instance dari AppRepository
        fun getInstance(
            appDao: AppDao,
            appExecutors: ExecutorsUtils
        ): AppRepository =
            // Jika instance null, maka akan dibuat instance baru
            instance ?: synchronized(this) {
                instance ?: AppRepository(appDao, appExecutors)
            }.also { instance = it }
    }
}