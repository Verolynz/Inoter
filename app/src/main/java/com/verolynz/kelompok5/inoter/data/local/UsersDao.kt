package com.verolynz.kelompok5.inoter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUsers(usersEntity: UsersEntity)

    @Update
    fun updateUsers(usersEntity: UsersEntity)

    @Delete
    fun deleteUsers(usersEntity: UsersEntity)

    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<UsersEntity>>
}