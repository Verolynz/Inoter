package com.verolynz.kelompok5.inoter.room


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface AppDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArtikel(player: ArtikelDatabase)


    @Query("SELECT * from ArtikelDatabase ORDER BY id ASC")
    fun getAllArtikel(): LiveData<List<ArtikelDatabase>>

    @Update
    fun updateArtikel(player: ArtikelDatabase)

    @Delete
    fun deleteArtikel(player: ArtikelDatabase)


}