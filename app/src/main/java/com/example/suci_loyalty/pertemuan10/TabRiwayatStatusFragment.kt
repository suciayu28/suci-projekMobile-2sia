package com.example.suci_loyalty.pertemuan10

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suci_loyalty.R
// 1. Pastikan import sesuai nama file: activity_tab_riwayat_status_fragment
import com.example.suci_loyalty.databinding.ActivityTabRiwayatStatusFragmentBinding

// 2. Pastikan layout yang dipanggil juga sesuai nama filenya
class TabRiwayatStatusFragment : Fragment(R.layout.activity_tab_riwayat_status_fragment) {

    // 3. Gunakan class binding yang dihasilkan dari nama file XML tersebut
    private var _binding: ActivityTabRiwayatStatusFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 4. Lakukan bind dengan class yang benar
        _binding = ActivityTabRiwayatStatusFragmentBinding.bind(view)

        // Data dummy sesuai kolom tabel riwayat_status_surat di database
        val listRiwayat = listOf(
            RiwayatSuratModel("SRT001", "Selesai", "2026-05-20"),
            RiwayatSuratModel("SRT002", "Proses", "2026-05-19"),
            RiwayatSuratModel("SRT003", "Ditolak", "2026-05-18")
        )

        val adapter = RiwayatSuratAdapter(listRiwayat)
        binding.rvRiwayatStatus.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRiwayatStatus.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}