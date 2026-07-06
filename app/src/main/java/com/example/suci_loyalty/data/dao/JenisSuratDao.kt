package com.example.suci_loyalty.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suci_loyalty.data.entity.JenisSuratEntity

@Dao
interface JenisSuratDao {
    @Query("SELECT * FROM jenis_surat")
    suspend fun getAll(): List<JenisSuratEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jenisSurat: JenisSuratEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<JenisSuratEntity>)

    @Update
    suspend fun update(jenisSurat: JenisSuratEntity)

    @Delete
    suspend fun delete(jenisSurat: JenisSuratEntity)
}
