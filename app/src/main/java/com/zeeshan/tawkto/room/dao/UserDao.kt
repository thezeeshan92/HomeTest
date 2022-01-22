package com.zeeshan.tawkto.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zeeshan.tawkto.room.entity.User


@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE id = :id")
    fun getSingleUser(id: Int): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(user: List<User>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("SELECT note FROM User WHERE login = :username")
    fun getSingleUserNote(username: String): LiveData<String>

    @Query("Update User set note =:note WHERE login = :username")
    suspend fun updateNote(username: String, note: String)

}