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

    @Update
    fun updateAtlet(atletEntity: AtletEntity)

    @Delete
    fun deleteAtlet(atletEntity: AtletEntity)

    @Query("SELECT * FROM atletentity ORDER BY id DESC")
    fun getAllAtlet() : LiveData<List<AtletEntity>>
}