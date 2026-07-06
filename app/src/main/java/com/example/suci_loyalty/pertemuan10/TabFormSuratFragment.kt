package com.example.suci_loyalty.pertemuan10

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.suci_loyalty.R
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.data.entity.PermohonanSuratEntity
import com.example.suci_loyalty.databinding.ActivityTabFormSuratFragmentBinding
import com.example.suci_loyalty.utils.NotificationHelper
import com.example.suci_loyalty.utils.ReminderHelper
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TabFormSuratFragment : Fragment(R.layout.activity_tab_form_surat_fragment) {
    private var _binding: ActivityTabFormSuratFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase

    // PERTEMUAN 13: URI foto KTP yang diambil dari kamera
    private var fotoKtpUri: Uri? = null

    // ---- Permission launcher untuk kamera ----
    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) bukaKamera()
        else Toast.makeText(requireContext(), "Izin kamera diperlukan untuk foto dokumen.", Toast.LENGTH_SHORT).show()
    }

    // ---- Result launcher untuk kamera ----
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Tampilkan preview foto dokumen di card
            binding.ivFotoDokumen.setImageURI(fotoKtpUri)
            binding.cardFotoDokumen.visibility = View.VISIBLE
            binding.btnFotoKtp.text = "📷  Ganti Foto Dokumen"
            Toast.makeText(requireContext(), "Foto dokumen berhasil diambil ✓", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ActivityTabFormSuratFragmentBinding.bind(view)
        db = AppDatabase.getInstance(requireContext())

        // Ambil jenis surat ter-update dari activity
        updateSelectedLetter()

        // PERTEMUAN 13: Tombol foto KTP/dokumen
        binding.btnFotoKtp.setOnClickListener {
            cekIzinDanBukaKamera()
        }

        binding.btnSubmitPermohonan.setOnClickListener {
            val jenisSurat = binding.etJenisSurat.text.toString().trim()
            val nik = binding.etNikPermohonan.text.toString().trim()
            val keperluan = binding.etKeperluan.text.toString().trim()

            if (nik.isEmpty()) {
                binding.etNikPermohonan.error = "NIK tidak boleh kosong"
                return@setOnClickListener
            }
            if (nik.length != 16) {
                binding.etNikPermohonan.error = "NIK harus terdiri dari 16 digit"
                return@setOnClickListener
            }
            if (keperluan.isEmpty()) {
                binding.etKeperluan.error = "Keperluan tidak boleh kosong"
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val timestamp = System.currentTimeMillis()
                val nomorPermohonan = "SRT" + (timestamp % 1000000).toString()
                val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                val newPermohonan = PermohonanSuratEntity(
                    nomorPermohonan = nomorPermohonan,
                    jenisSurat = jenisSurat,
                    nik = nik,
                    keperluan = keperluan,
                    status = "Proses",
                    waktu = dateStr,
                    keterangan = "Menunggu verifikasi RT/RW"
                )

                db.permohonanSuratDao().insert(newPermohonan)

                Toast.makeText(requireContext(), "Permohonan Surat Berhasil Dikirim!", Toast.LENGTH_SHORT).show()

                // Kirim notifikasi instan
                val notifIntent = Intent(requireContext(), PermohonanSuratActivity::class.java)
                NotificationHelper.showNotification(
                    requireContext(),
                    "SakuSurat - Pengajuan Sukses",
                    "Pengajuan surat $jenisSurat ($nomorPermohonan) telah terkirim.",
                    notifIntent
                )

                // Set pengingat evaluasi 1 menit lagi
                val calendar = Calendar.getInstance().apply {
                    add(Calendar.MINUTE, 1)
                }
                ReminderHelper.setReminder(
                    context = requireContext(),
                    hour = calendar.get(Calendar.HOUR_OF_DAY),
                    minute = calendar.get(Calendar.MINUTE),
                    title = "Evaluasi Status SakuSurat",
                    message = "Silakan periksa status tindak lanjut permohonan surat Anda.",
                    targetActivity = PermohonanSuratActivity::class.java
                )

                // Reset field
                binding.etNikPermohonan.text?.clear()
                binding.etKeperluan.text?.clear()
                binding.etNikPermohonan.error = null
                binding.etKeperluan.error = null
                binding.cardFotoDokumen.visibility = View.GONE
                binding.btnFotoKtp.text = "📷  Ambil Foto KTP / Dokumen"
                fotoKtpUri = null

                // Switch ke tab 2 (Riwayat Status)
                val act = activity as? PermohonanSuratActivity
                act?.switchToHistoryTab()
            }
        }
    }

    /**
     * PERTEMUAN 13: Cek izin kamera sebelum membuka kamera.
     * Jika sudah ada izin → langsung buka kamera.
     * Jika belum → minta izin ke pengguna.
     */
    private fun cekIzinDanBukaKamera() {
        when {
            ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> bukaKamera()
            else -> requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    /**
     * PERTEMUAN 13: Membuka Intent kamera dengan FileProvider.
     * Foto disimpan ke folder internal app (bukan path langsung)
     * agar aman dibagikan ke Intent kamera bawaan Android.
     */
    private fun bukaKamera() {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val folderGambar = File(requireContext().filesDir, "Pictures").also { it.mkdirs() }
        val fileGambar = File(folderGambar, "KTP_$timestamp.jpg")

        // Konversi File → URI aman menggunakan FileProvider
        fotoKtpUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            fileGambar
        )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, fotoKtpUri)
        }
        cameraLauncher.launch(intent)
    }

    fun updateSelectedLetter() {
        if (_binding != null) {
            val act = activity as? PermohonanSuratActivity
            act?.let {
                binding.etJenisSurat.setText(it.selectedLetterName)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateSelectedLetter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}