package com.example.suci_loyalty.pertemuan10

// Menggunakan data class untuk menyimpan data riwayat status surat
data class RiwayatSuratModel(
    val nomorPermohonan: String, // Dari kolom nomor_permohonan
    val status: String,          // Dari kolom status
    val waktu: String,           // Dari kolom waktu
    val keterangan: String = ""  // Dari kolom keterangan
)