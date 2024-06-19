package com.verolynz.kelompok5.inoter.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.verolynz.kelompok5.inoter.utils.Converters
import retrofit2.Converter


@Database(entities = [ArtikelDatabase::class], version = 2)

// Menggunakan konverter tipe khusus untuk mengubah tipe data File menjadi String dan sebaliknya
@TypeConverters(Converters::class)

// Membuat kelas abstrak AppDatabase yang merupakan turunan dari RoomDatabase
abstract class AppDatabase : RoomDatabase() {

    // Mendeklarasikan fungsi abstrak yang mengembalikan AppDao
    abstract fun appDao(): AppDao

    // Membuat objek pendamping untuk AppDatabase
    companion object {
        // Mendeklarasikan variabel INSTANCE yang akan menyimpan instance dari AppDatabase
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Membuat fungsi statis untuk mendapatkan instance database
        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            // Jika INSTANCE null, maka akan dibuat instance baru
            if (INSTANCE == null) {
                // Menggunakan synchronized untuk mencegah akses bersamaan dari beberapa thread
                synchronized(AppDatabase::class.java) {
                    // Membuat instance baru dari AppDatabase
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            // Mengembalikan instance dari AppDatabase
            return INSTANCE as AppDatabase
        }
    }
}