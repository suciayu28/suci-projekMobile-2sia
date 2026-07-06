package com.example.suci_loyalty.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "permohonan_surat")
data class PermohonanSuratEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomorPermohonan: String,
    val jenisSurat: String,
    val nik: String,
    val keperluan: String,
    val status: String,
    val waktu: String,
    val keterangan: String = ""
)
