package com.example.suci_loyalty.pertemuan10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PermohonanSuratAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Kita buat 3 Tab sesuai kebutuhan fitur Layanan Mandiri
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabJenisSuratFragment()   // Menampilkan daftar jenis_surat
            1 -> TabFormSuratFragment()    // Form untuk permohonan_surat
            2 -> TabRiwayatStatusFragment() // Menampilkan riwayat_status_surat
            else -> TabJenisSuratFragment()
        }
    }
}