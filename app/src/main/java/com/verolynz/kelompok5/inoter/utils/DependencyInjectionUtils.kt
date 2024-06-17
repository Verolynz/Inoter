package com.verolynz.kelompok5.inoter.utils

import android.content.Context
import com.verolynz.kelompok5.inoter.data.local.OlahragaDB
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository
import com.verolynz.kelompok5.inoter.room.AppDatabase
import com.verolynz.kelompok5.inoter.room.AppRepository

//apa yang dilakukan oleh DependencyInjectionUtils adalah memberikan repository yang dibutuhkan oleh ViewModels
//hola amigass
//pepek


//apa yang dilakukan oleh DependencyInjectionUtils adalah memberikan repository yang dibutuhkan oleh ViewModels




//hola amigass
//pepek


object DependencyInjectionUtils {

    fun provideRepository(context: Context) : OlahragaRepository {
        val database = OlahragaDB.getDatabase(context)
        val executorsUtils = ExecutorsUtils()
        val coDao = database.CODao()
        val atletDao = database.AtletDao()
        val userDao = database.UsersDao()
        return OlahragaRepository.getInstance(coDao, atletDao, userDao ,executorsUtils)
    }
    fun provideRepositoryArtikel(context: Context): AppRepository {
        // Membuat instance dari AppDatabase
        val database = AppDatabase.getDatabase(context)
        // Membuat instance dari AppExecutors
        val appExecutors = ExecutorsUtils()
        // Mendapatkan instance dari AppDao dari AppDatabase
        val dao = database.appDao()
        // Mendapatkan instance dari AppRepository menggunakan AppDao dan AppExecutors
        return AppRepository.getInstance(dao, appExecutors)
    }
}