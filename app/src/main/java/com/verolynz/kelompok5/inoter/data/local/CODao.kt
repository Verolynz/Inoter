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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCO(data: List<COEntity>)

    @Update
    fun updateCO(cOEntity: COEntity)


    @Delete
    fun deleteCO(cOEntity: COEntity)
    @Query("DELETE FROM cabang_olahraga")
    fun deleteAllCO()

    @Query("DELETE FROM cabang_olahraga WHERE id = :id")
    fun deleteCOById(id: Int)

    @Query("SELECT * FROM cabang_olahraga ORDER BY id DESC")
    fun getAllCO() : LiveData<List<COEntity>>
    @Query("SELECT * FROM cabang_olahraga ORDER BY id DESC")
    fun getAllCOList() : List<COEntity>



}