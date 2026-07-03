package com.example.suci_loyalty.pertemuan10

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suci_loyalty.R
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.data.entity.JenisSuratEntity
import com.example.suci_loyalty.databinding.ActivityTabJenisSuratFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class TabJenisSuratFragment : Fragment(R.layout.activity_tab_jenis_surat_fragment) {
    private var _binding: ActivityTabJenisSuratFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ActivityTabJenisSuratFragmentBinding.bind(view)
        db = AppDatabase.getInstance(requireContext())

        binding.rvJenisSurat.layoutManager = LinearLayoutManager(requireContext())

        // 1. Klik Tambah Jenis Surat baru
        binding.btnAddJenis.setOnClickListener {
            showAddEditJenisSuratDialog()
        }

        loadJenisSurat()
    }

    private fun loadJenisSurat() {
        lifecycleScope.launch {
            var list = db.jenisSuratDao().getAll()
            if (list.isEmpty()) {
                // Pre-populate data awal agar tidak kosong saat dibuka pertama kali
                val defaults = listOf(
                    JenisSuratEntity("SKD", "Surat Keterangan Domisili", "Digunakan untuk keperluan administrasi kependudukan penduduk sementara."),
                    JenisSuratEntity("SKU", "Surat Keterangan Usaha", "Syarat utama pengajuan kredit usaha atau legalitas usaha kecil menengah."),
                    JenisSuratEntity("SKTM", "Surat Ket. Tidak Mampu", "Digunakan untuk mendapatkan bantuan sosial atau keringanan biaya pendidikan."),
                    JenisSuratEntity("SKP", "Surat Keterangan Pindah", "Dokumen resmi bagi warga yang ingin pindah domisili antar kota/provinsi."),
                    JenisSuratEntity("SKIK", "Surat Izin Keramaian", "Izin resmi untuk mengadakan acara yang melibatkan banyak orang di lingkungan desa."),
                    JenisSuratEntity("SKCK", "Pengantar SKCK", "Surat pengantar dari desa sebagai syarat pembuatan SKCK di kepolisian.")
                )
                db.jenisSuratDao().insertAll(defaults)
                list = db.jenisSuratDao().getAll()
            }

            val adapter = JenisSuratAdapter(
                list,
                onSelectClick = { item ->
                    // Klik tombol DETAIL/PILIH: pilih jenis surat lalu arahkan ke form pengajuan
                    val act = activity as? PermohonanSuratActivity
                    act?.selectedLetterName = item.nama
                    act?.switchToFormTab()
                },
                onEditClick = { item ->
                    // Klik tombol EDIT
                    showAddEditJenisSuratDialog(item)
                },
                onDeleteClick = { item ->
                    // Klik tombol HAPUS
                    confirmDeleteJenisSurat(item)
                }
            )
            binding.rvJenisSurat.adapter = adapter
        }
    }

    private fun showAddEditJenisSuratDialog(item: JenisSuratEntity? = null) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_edit_jenis_surat, null)
        val etKode = dialogView.findViewById<EditText>(R.id.et_kode)
        val etNama = dialogView.findViewById<EditText>(R.id.et_nama)
        val etDeskripsi = dialogView.findViewById<EditText>(R.id.et_deskripsi)

        if (item != null) {
            etKode.setText(item.kode)
            etKode.isEnabled = false // Kode adalah Primary Key, tidak bisa diubah
            etNama.setText(item.nama)
            etDeskripsi.setText(item.deskripsi)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (item == null) "Tambah Jenis Surat" else "Edit Jenis Surat")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val kode = etKode.text.toString().trim()
                val nama = etNama.text.toString().trim()
                val deskripsi = etDeskripsi.text.toString().trim()

                if (kode.isEmpty() || nama.isEmpty() || deskripsi.isEmpty()) {
                    Toast.makeText(requireContext(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                lifecycleScope.launch {
                    val entity = JenisSuratEntity(kode, nama, deskripsi)
                    if (item == null) {
                        db.jenisSuratDao().insert(entity)
                        Toast.makeText(requireContext(), "Jenis surat berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    } else {
                        db.jenisSuratDao().update(entity)
                        Toast.makeText(requireContext(), "Jenis surat berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                    }
                    loadJenisSurat()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun confirmDeleteJenisSurat(item: JenisSuratEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Hapus Jenis Surat")
            .setMessage("Apakah Anda yakin ingin menghapus jenis surat \"${item.nama}\"?")
            .setPositiveButton("Ya, Hapus") { _, _ ->
                lifecycleScope.launch {
                    db.jenisSuratDao().delete(item)
                    Toast.makeText(requireContext(), "Jenis surat berhasil dihapus!", Toast.LENGTH_SHORT).show()
                    loadJenisSurat()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}