package com.uts.inventaris053.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uts.inventaris053.data.entity.Item_053

/**
 * DAO untuk operasi CRUD Inventaris Barang (Item_053)
 * Seluruh query memfilter berdasarkan user_id_053 agar data terisolasi per user.
 */
@Dao
interface ItemDao_053 {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem_053(item_053: Item_053): Long

    @Update
    suspend fun updateItem_053(item_053: Item_053)

    @Delete
    suspend fun deleteItem_053(item_053: Item_053)

    @Query("SELECT * FROM item_053 WHERE user_id_053 = :userId_053 ORDER BY id_053 DESC")
    fun getItemsByUser_053(userId_053: Int): LiveData<List<Item_053>>

    @Query("SELECT * FROM item_053 WHERE user_id_053 = :userId_053 AND nama_053 LIKE '%' || :keyword_053 || '%' ORDER BY id_053 DESC")
    fun searchItemsByUser_053(userId_053: Int, keyword_053: String): LiveData<List<Item_053>>

    @Query("SELECT * FROM item_053 WHERE id_053 = :itemId_053 AND user_id_053 = :userId_053 LIMIT 1")
    suspend fun getItemById_053(itemId_053: Int, userId_053: Int): Item_053?

    /**
     * Query Bonus Kelompok 4:
     * Menghitung total nilai seluruh inventaris (SUM(jumlah_053 * harga_053)) secara real-time untuk user yang login.
     */
    @Query("SELECT COALESCE(SUM(jumlah_053 * harga_053), 0) FROM item_053 WHERE user_id_053 = :userId_053")
    fun getTotalInventoryValue_053(userId_053: Int): LiveData<Long>
}

