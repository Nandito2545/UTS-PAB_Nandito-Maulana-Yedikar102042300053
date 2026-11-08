package com.uts.inventaris053.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity Item (Barang) untuk Kelompok 4 - Inventaris Barang
 * NIM: 102042300053 (3 digit terakhir: 053)
 * Menggunakan user_id_053 sebagai Foreign Key ke tabel user_053 untuk isolasi data antar user.
 */
@Entity(
    tableName = "item_053",
    foreignKeys = [
        ForeignKey(
            entity = User_053::class,
            parentColumns = ["id_053"],
            childColumns = ["user_id_053"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id_053"])]
)
data class Item_053(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_053")
    val id_053: Int = 0,

    @ColumnInfo(name = "user_id_053")
    val user_id_053: Int,

    @ColumnInfo(name = "nama_053")
    val nama_053: String,

    @ColumnInfo(name = "jumlah_053")
    val jumlah_053: Int,

    @ColumnInfo(name = "harga_053")
    val harga_053: Long
)

