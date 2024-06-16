package com.verolynz.kelompok5.inoter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [COEntity::class, AtletEntity::class, UsersEntity::class], version = 1)
abstract class OlahragaDB : RoomDatabase() {
    abstract fun CODao(): CODao
    abstract fun AtletDao(): AtletDao

    abstract fun UsersDao(): UsersDao

    companion object {

        @Volatile
        private var INSTANCE: OlahragaDB? = null


        @JvmStatic
        fun getDatabase(context: Context): OlahragaDB {
            // Jika INSTANCE null, maka akan dibuat instance baru
            if (INSTANCE == null) {
                // Menggunakan synchronized untuk mencegah akses bersamaan dari beberapa thread
                synchronized(OlahragaDB::class.java) {
                    // Membuat instance baru dari AppDatabase
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        OlahragaDB::class.java, "Olahraga_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            // Mengembalikan instance dari AppDatabase
            return INSTANCE as OlahragaDB
        }
    }
}