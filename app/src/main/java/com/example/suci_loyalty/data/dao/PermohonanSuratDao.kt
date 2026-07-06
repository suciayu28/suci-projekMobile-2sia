package com.example.suci_loyalty.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.suci_loyalty.data.entity.PermohonanSuratEntity

@Dao
interface PermohonanSuratDao {
    @Query("SELECT * FROM permohonan_surat ORDER BY id DESC")
    suspend fun getAll(): List<PermohonanSuratEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(permohonan: PermohonanSuratEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PermohonanSuratEntity>)

    @Update
    suspend fun update(permohonan: PermohonanSuratEntity)

    @Delete
    suspend fun delete(permohonan: PermohonanSuratEntity)
}
