package com.example.suci_loyalty.pertemuan10

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.suci_loyalty.R
// Sesuaikan dengan nama file XML Anda: activity_tab_jenis_surat_fragment
import com.example.suci_loyalty.databinding.ActivityTabJenisSuratFragmentBinding

class TabJenisSuratFragment : Fragment(R.layout.activity_tab_jenis_surat_fragment) {
    private var _binding: ActivityTabJenisSuratFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ActivityTabJenisSuratFragmentBinding.bind(view)

        val listJenisSurat = listOf(
            JenisSuratModel("SKD", "Surat Keterangan Domisili", "Digunakan untuk keperluan administrasi kependudukan penduduk sementara."),
            JenisSuratModel("SKU", "Surat Keterangan Usaha", "Syarat utama pengajuan kredit usaha atau legalitas usaha kecil menengah."),
            JenisSuratModel("SKTM", "Surat Ket. Tidak Mampu", "Digunakan untuk mendapatkan bantuan sosial atau keringanan biaya pendidikan."),
            JenisSuratModel("SKP", "Surat Keterangan Pindah", "Dokumen resmi bagi warga yang ingin pindah domisili antar kota/provinsi."),
            JenisSuratModel("SKIK", "Surat Izin Keramaian", "Izin resmi untuk mengadakan acara yang melibatkan banyak orang di lingkungan desa."),
            JenisSuratModel("SKCK", "Pengantar SKCK", "Surat pengantar dari desa sebagai syarat pembuatan SKCK di kepolisian.")
        )

        val adapter = JenisSuratAdapter(listJenisSurat)

        // --- TAMBAHKAN BARIS INI ---
        binding.rvJenisSurat.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.rvJenisSurat.adapter = adapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}