package com.verolynz.kelompok5.inoter.utils

import android.content.Context
import com.verolynz.kelompok5.inoter.data.local.OlahragaDB
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository


object DependencyInjectionUtils {

    fun provideRepository(context: Context) : OlahragaRepository {
        val database = OlahragaDB.getDatabase(context)
        val ExecutorsUtils = ExecutorsUtils()
        val coDao = database.CODao()
        val atletDao = database.AtletDao()
        return OlahragaRepository.getInstance(coDao, atletDao, ExecutorsUtils)
    }

}