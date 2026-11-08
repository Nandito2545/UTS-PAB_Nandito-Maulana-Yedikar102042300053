package com.uts.inventaris053.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uts.inventaris053.data.dao.ItemDao_053
import com.uts.inventaris053.data.dao.UserDao_053
import com.uts.inventaris053.data.entity.Item_053
import com.uts.inventaris053.data.entity.User_053

/**
 * Room Database Utama untuk Aplikasi Inventaris Barang 053
 * NIM: 102042300053
 */
@Database(
    entities = [User_053::class, Item_053::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase_053 : RoomDatabase() {

    abstract fun userDao_053(): UserDao_053
    abstract fun itemDao_053(): ItemDao_053

    companion object {
        @Volatile
        private var INSTANCE_053: AppDatabase_053? = null

        fun getDatabase_053(context_053: Context): AppDatabase_053 {
            return INSTANCE_053 ?: synchronized(this) {
                val instance_053 = Room.databaseBuilder(
                    context_053.applicationContext,
                    AppDatabase_053::class.java,
                    "inventaris_database_053"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE_053 = instance_053
                instance_053
            }
        }
    }
}

