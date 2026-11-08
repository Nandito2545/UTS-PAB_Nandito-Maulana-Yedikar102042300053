package com.uts.inventaris053.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uts.inventaris053.data.entity.User_053

/**
 * DAO untuk autentikasi User (Register & Login)
 * NIM: 102042300053 (053)
 */
@Dao
interface UserDao_053 {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun registerUser_053(user_053: User_053): Long

    @Query("SELECT * FROM user_053 WHERE username_053 = :username_053 AND password_053 = :password_053 LIMIT 1")
    suspend fun loginUser_053(username_053: String, password_053: String): User_053?

    @Query("SELECT * FROM user_053 WHERE username_053 = :username_053 LIMIT 1")
    suspend fun getUserByUsername_053(username_053: String): User_053?
}

