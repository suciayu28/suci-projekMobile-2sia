package com.example.suci_loyalty.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jenis_surat")
data class JenisSuratEntity(
    @PrimaryKey val kode: String,
    val nama: String,
    val deskripsi: String
)
