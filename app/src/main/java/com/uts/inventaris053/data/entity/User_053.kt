package com.uts.inventaris053.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity User untuk Kelompok 4 - Inventaris Barang
 * NIM: 102042300053 (3 digit terakhir: 053)
 */
@Entity(
    tableName = "user_053",
    indices = [Index(value = ["username_053"], unique = true)]
)
data class User_053(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_053")
    val id_053: Int = 0,

    @ColumnInfo(name = "username_053")
    val username_053: String,

    @ColumnInfo(name = "password_053")
    val password_053: String
)

