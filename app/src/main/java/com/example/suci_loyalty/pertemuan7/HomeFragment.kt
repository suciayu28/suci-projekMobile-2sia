package com.example.suci_loyalty.pertemuan7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter // Tambahkan import ini
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.suci_loyalty.R // Pastikan R terimport dengan benar
import com.example.suci_loyalty.databinding.ActivityHomeFragmentBinding
import com.example.suci_loyalty.pertemuan3.LoginActivity
import com.example.suci_loyalty.pertemuan4.Custom1Activity
import com.example.suci_loyalty.pertemuan4.Custom2Activity
import com.example.suci_loyalty.pertemuan4.RumusActivity
import com.example.suci_loyalty.pertemuan6.WebViewActivity
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {

    private var _binding: ActivityHomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Tombol WebView
        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        // 2. Tombol Rumus
        binding.btnRumus.setOnClickListener {
            startActivity(Intent(requireContext(), RumusActivity::class.java))
        }

        // 3. Tombol Custom 1 (Profil Desa)
        binding.btnCustom1.setOnClickListener {
            startActivity(Intent(requireContext(), Custom1Activity::class.java))
        }

        // 4. Tombol Custom 2 (Bantuan)
        binding.btnCustom2.setOnClickListener {
            startActivity(Intent(requireContext(), Custom2Activity::class.java))
        }

        // 5. Logika ChipGroup (Kategori Surat)
        binding.chipGroupLayanan.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chip: Chip = group.findViewById(checkedIds[0])
                Toast.makeText(requireContext(), "Kategori Terpilih: ${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // 6. Tombol Buat Surat
        binding.btnSuratMandiri.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka Form Pengajuan Surat", Toast.LENGTH_SHORT).show()
        }

        // 7. Tombol Status Surat
        binding.btnStatusSurat.setOnClickListener {
            Toast.makeText(requireContext(), "Cek Status: Dalam Proses Verifikasi", Toast.LENGTH_SHORT).show()
        }

        // --- LOGIKA BARU UNTUK TextInputLayout & SUBMIT FORM (MODUL 9) ---

        binding.btnSubmitSurat.setOnClickListener {
            val nik = binding.etNikSurat.text.toString()
            val alasan = binding.etAlasanSurat.text.toString()

            if (nik.isEmpty() || alasan.isEmpty()) {
                if (nik.isEmpty()) binding.etNikSurat.error = "NIK tidak boleh kosong"
                if (alasan.isEmpty()) binding.etAlasanSurat.error = "Alasan tidak boleh kosong"

                Toast.makeText(requireContext(), "Harap lengkapi semua data", Toast.LENGTH_SHORT).show()
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Berhasil")
                    .setMessage("Pengajuan surat dengan NIK $nik telah dikirim ke server Desa.")
                    .setPositiveButton("OK") { dialog, _ ->
                        binding.etNikSurat.text?.clear()
                        binding.etAlasanSurat.text?.clear()
                        binding.etNikSurat.error = null
                        binding.etAlasanSurat.error = null
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        // --- LOGIKA LISTVIEW (SIMPLE ADAPTER) UNTUK PUSAT INFORMASI ---

        val menuNames = arrayOf("Kebijakan Privasi", "Tentang Aplikasi", "Syarat & Ketentuan", "Hubungi Kami")
        val menuIcons = arrayOf(
            android.R.drawable.ic_lock_lock,
            android.R.drawable.ic_menu_info_details,
            android.R.drawable.ic_menu_agenda,
            android.R.drawable.ic_menu_call
        )

        val listData = mutableListOf<Map<String, Any>>()

        for (i in menuNames.indices) {
            val map = mutableMapOf<String, Any>()
            map["name"] = menuNames[i]
            map["icon"] = menuIcons[i]
            listData.add(map)
        }

        val adapter = SimpleAdapter(
            requireContext(),
            listData,
            R.layout.list_item_info, // Pastikan layout ini sudah dibuat
            arrayOf("name", "icon"),
            intArrayOf(R.id.txtMenuName, R.id.imgIcon)
        )

        binding.listViewInfo.adapter = adapter

        binding.listViewInfo.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(requireContext(), "Membuka: ${menuNames[position]}", Toast.LENGTH_SHORT).show()
        }

        // --- AKHIR LOGIKA LISTVIEW ---

        // 8. Tombol Logout
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Yakin ingin logout dari aplikasi Bina Desa?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = requireActivity().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    sharedPref.edit().clear().apply()

                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}