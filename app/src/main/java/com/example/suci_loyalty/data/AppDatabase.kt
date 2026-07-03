package com.example.suci_loyalty.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.suci_loyalty.data.dao.NoteDao
import com.example.suci_loyalty.data.entity.NoteEntity
import com.example.suci_loyalty.data.entity.ComplaintEntity
import com.example.suci_loyalty.data.dao.ComplaintDao
import com.example.suci_loyalty.data.entity.JenisSuratEntity
import com.example.suci_loyalty.data.entity.PermohonanSuratEntity
import com.example.suci_loyalty.data.dao.JenisSuratDao
import com.example.suci_loyalty.data.dao.PermohonanSuratDao

@Database(
    entities = [NoteEntity::class, ComplaintEntity::class, JenisSuratEntity::class, PermohonanSuratEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun complaintDao(): ComplaintDao
    abstract fun jenisSuratDao(): JenisSuratDao
    abstract fun permohonanSuratDao(): PermohonanSuratDao

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
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}