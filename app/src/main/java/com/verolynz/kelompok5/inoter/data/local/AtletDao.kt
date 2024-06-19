package com.verolynz.kelompok5.inoter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AtletDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAtlet(atletEntity: AtletEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAtlet(data: List<AtletEntity>)
    @Update
    fun updateAtlet(atletEntity: AtletEntity)

    @Delete
    fun deleteAtlet(atletEntity: AtletEntity)
    @Query("DELETE FROM atlet")
    fun deleteAllAtlet()

    @Query("DELETE FROM atlet WHERE id = :id")
    fun deleteAtletById(id: Int)

    @Query("SELECT * FROM atlet ORDER BY id DESC")
    fun getAllAtlet() : LiveData<List<AtletEntity>>
    @Query("SELECT * FROM atlet ORDER BY id DESC")
    fun getAllAtletList() : List<AtletEntity>
}