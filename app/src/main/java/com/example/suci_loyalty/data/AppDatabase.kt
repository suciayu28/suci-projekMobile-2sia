package com.example.suci_loyalty.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.suci_loyalty.data.dao.NoteDao
import com.example.suci_loyalty.data.entity.NoteEntity
import com.example.suci_loyalty.data.entity.ComplaintEntity
import com.example.suci_loyalty.data.dao.ComplaintDao

@Database(
    entities = [NoteEntity::class, ComplaintEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun complaintDao(): ComplaintDao // ➔ Menyelipkan abstract fungsi ini

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // ➔ Menyelipkan ini agar aplikasi tidak crash saat versinya naik ke 2
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}