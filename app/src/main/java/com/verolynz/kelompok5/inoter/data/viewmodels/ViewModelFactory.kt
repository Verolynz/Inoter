package com.verolynz.kelompok5.inoter.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.verolynz.kelompok5.inoter.data.repositories.OlahragaRepository
import com.verolynz.kelompok5.inoter.utils.DependencyInjectionUtils

class ViewModelFactory private constructor(private val olahragaRepository: OlahragaRepository) :
    ViewModelProvider.NewInstanceFactory() {
    // Fungsi ini digunakan untuk membuat instance dari ViewModel
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Jika modelClass adalah atau merupakan subclass dari ExampleViewModel
        if (modelClass.isAssignableFrom(OlahragaViewModels::class.java)) {
            // Membuat dan mengembalikan instance dari ExampleViewModel
            return OlahragaViewModels(olahragaRepository) as T
        }
        // Jika modelClass bukan ExampleViewModel atau subclass dari ExampleViewModel, lemparkan pengecualian
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    // Objek companion yang digunakan untuk membuat instance dari ExampleViewModelFactory
    companion object {
        // Variabel instance digunakan untuk menyimpan instance dari ExampleViewModelFactory
        @Volatile
        private var instance: ViewModelFactory? = null

        // Fungsi ini digunakan untuk mendapatkan instance dari ExampleViewModelFactory
        fun getInstance(context: Context): ViewModelFactory =
        // Jika instance tidak null, kembalikan instance
            // Jika instance null, buat instance baru
            instance ?: synchronized(this) {
                instance
                    ?: ViewModelFactory(DependencyInjectionUtils.provideRepository(context))
            }.also { instance = it }
    }
}