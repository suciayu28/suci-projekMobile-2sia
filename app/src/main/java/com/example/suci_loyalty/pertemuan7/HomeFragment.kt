package com.example.suci_loyalty.pertemuan7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suci_loyalty.R
import com.example.suci_loyalty.databinding.ActivityHomeFragmentBinding
import com.example.suci_loyalty.onboarding.NewsAdapter
import com.example.suci_loyalty.pertemuan10.PermohonanSuratActivity
import com.example.suci_loyalty.pertemuan3.LoginActivity
import com.example.suci_loyalty.pertemuan4.Custom1Activity
import com.example.suci_loyalty.pertemuan4.Custom2Activity
import com.example.suci_loyalty.pertemuan6.WebViewActivity
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // 2. Tombol ajukan surat
        binding.btnAjukanSurat.setOnClickListener {
            startActivity(Intent(requireContext(), PermohonanSuratActivity::class.java))
        }

        // 3. Tombol Custom 1 (Profil Desa)
        binding.btnCustom1.setOnClickListener {
            val intent = Intent(requireContext(), Custom1Activity::class.java).apply {
                putExtra("title", "Profil Desa")
                putExtra("desc", "Detail informasi kependudukan dan profil pemerintahan Desa Maju Jaya.")
            }
            startActivity(intent)
        }

        // 4. Tombol Custom 2 (Bantuan)
        binding.btnCustom2.setOnClickListener {
            val intent = Intent(requireContext(), Custom2Activity::class.java).apply {
                putExtra("title", "Pusat Bantuan")
                putExtra("desc", "Pilih dan hitung volume/luas bangun ruang secara mandiri.")
            }
            startActivity(intent)
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
            val intent = Intent(requireContext(), PermohonanSuratActivity::class.java).apply {
                putExtra("target_tab", 1) // Form Pengajuan
            }
            startActivity(intent)
        }

        // 7. Tombol Status Surat
        binding.btnStatusSurat.setOnClickListener {
            val intent = Intent(requireContext(), PermohonanSuratActivity::class.java).apply {
                putExtra("target_tab", 2) // Riwayat Status
            }
            startActivity(intent)
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
            R.layout.list_item_info,
            arrayOf("name", "icon"),
            intArrayOf(R.id.txtMenuName, R.id.imgIcon)
        )

        binding.listViewInfo.adapter = adapter

        binding.listViewInfo.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(requireContext(), "Membuka: ${menuNames[position]}", Toast.LENGTH_SHORT).show()
        }

        // --- AKHIR LOGIKA LISTVIEW ---
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())

        val fallbackNews = listOf(
            NewsItem(
                title = "Pembangunan Gedung Balai Desa Bina Desa Resmi Dimulai",
                description = "Pemerintah desa meresmikan proyek renovasi ruang pelayanan publik guna meningkatkan kenyamanan masyarakat dalam mengurus dokumen administrasi kependudukan.",
                image = "https://images.unsplash.com/photo-1541872703-74c5e44368f9",
                link = "https://google.com"
            ),
            NewsItem(
                title = "Jadwal Pengambilan Bansos Sembako Bulan Ini",
                description = "Bagi warga yang terdaftar sebagai keluarga penerima manfaat (KPM), proses pembagian sembako akan dilaksanakan serentak mulai hari Senin depan di aula utama desa.",
                image = "https://images.unsplash.com/photo-1593113598332-cd288d649433",
                link = "https://google.com"
            ),
            NewsItem(
                title = "Kerja Bakti Massal Antisipasi Dampak Musim Hujan",
                description = "Kepala Desa menghimbau seluruh warga RT 01 hingga RT 08 untuk berpartisipasi aktif dalam aksi pembersihan saluran gorong-gorong desa pada akhir pekan ini.",
                image = "https://images.unsplash.com/photo-1582213782179-e0d53f98f2ca",
                link = "https://google.com"
            )
        )

        NewsApiService.create().getNews().enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val newsList = response.body()?.data
                    if (!newsList.isNullOrEmpty()) {
                        val filteredList = newsList.filter { !it.title.isNullOrEmpty() }
                        binding.rvNews.adapter = NewsAdapter(filteredList)
                        return
                    }
                }
                binding.rvNews.adapter = NewsAdapter(fallbackNews)
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                binding.rvNews.adapter = NewsAdapter(fallbackNews)
            }
        })
        // --- AKHIR LOGIKA RECYCLERVIEW ---

        // 8. Tombol Logout
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Yakin ingin logout dari aplikasi SakuSurat?")
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