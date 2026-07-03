package com.example.suci_loyalty.pertemuan10

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suci_loyalty.R
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.data.entity.PermohonanSuratEntity
import com.example.suci_loyalty.databinding.ActivityTabRiwayatStatusFragmentBinding
import kotlinx.coroutines.launch

class TabRiwayatStatusFragment : Fragment(R.layout.activity_tab_riwayat_status_fragment) {

    private var _binding: ActivityTabRiwayatStatusFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ActivityTabRiwayatStatusFragmentBinding.bind(view)
        db = AppDatabase.getInstance(requireContext())

        binding.rvRiwayatStatus.layoutManager = LinearLayoutManager(requireContext())
        fetchRiwayatStatus()
    }

    fun fetchRiwayatStatus() {
        if (_binding == null) return
        lifecycleScope.launch {
            var list = db.permohonanSuratDao().getAll()
            if (list.isEmpty()) {
                // Pre-populate data awal jika database kosong
                val defaults = listOf(
                    PermohonanSuratEntity(
                        nomorPermohonan = "SRT001",
                        jenisSurat = "Surat Keterangan Domisili",
                        nik = "1234567890123456",
                        keperluan = "Pindah Rumah",
                        status = "Selesai",
                        waktu = "2026-05-20",
                        keterangan = "Surat selesai ditandatangani"
                    ),
                    PermohonanSuratEntity(
                        nomorPermohonan = "SRT002",
                        jenisSurat = "Surat Keterangan Usaha",
                        nik = "1234567890123456",
                        keperluan = "Kredit BRI",
                        status = "Proses",
                        waktu = "2026-05-19",
                        keterangan = "Menunggu verifikasi RT/RW"
                    ),
                    PermohonanSuratEntity(
                        nomorPermohonan = "SRT003",
                        jenisSurat = "Surat Ket. Tidak Mampu",
                        nik = "1234567890123456",
                        keperluan = "Beasiswa Kuliah",
                        status = "Ditolak",
                        waktu = "2026-05-18",
                        keterangan = "Dokumen pendukung tidak lengkap"
                    )
                )
                db.permohonanSuratDao().insertAll(defaults)
                list = db.permohonanSuratDao().getAll()
            }

            val adapter = RiwayatSuratAdapter(list)
            binding.rvRiwayatStatus.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        fetchRiwayatStatus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}