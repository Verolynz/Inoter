package com.verolynz.kelompok5.inoter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CODao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCO(cOEntity: COEntity)

    @Update
    fun updateCO(cOEntity: COEntity)

    @Delete
    fun deleteCO(cOEntity: COEntity)

    @Query("SELECT * FROM cabang_olahraga ORDER BY id DESC")
    fun getAllCO() : LiveData<List<COEntity>>
}