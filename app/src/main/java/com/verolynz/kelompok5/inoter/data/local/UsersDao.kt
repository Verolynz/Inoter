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
    fun insertUsers(userEntity: UsersEntity)


    @Update
    fun updateUsers(userEntity: UsersEntity)


    @Delete
    fun deleteUsers(userEntity: UsersEntity)
    @Query("DELETE FROM users")
    fun deleteAllUsers()

    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAllUsers() : LiveData<List<UsersEntity>>

    @Query("SELECT * FROM users WHERE role = 'admin' ORDER BY id DESC")
    fun getUsersAdmin() : LiveData<List<UsersEntity>>

    @Query("SELECT * FROM users WHERE role = 'user' ORDER BY id DESC")
    fun getUsersUser() : LiveData<List<UsersEntity>>

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    fun getUsersAuth(username: String, password: String) : LiveData<UsersEntity>
}