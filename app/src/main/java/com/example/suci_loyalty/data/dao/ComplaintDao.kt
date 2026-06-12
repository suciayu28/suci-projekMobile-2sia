package com.example.suci_loyalty.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.suci_loyalty.data.entity.ComplaintEntity

@Dao
interface ComplaintDao {
    @Insert
    suspend fun insert(complaint: ComplaintEntity)

    @Query("SELECT * FROM complaints")
    suspend fun getAll(): List<ComplaintEntity>

    @Delete
    suspend fun delete(complaint: ComplaintEntity)
}