package com.example.suci_loyalty.pertemuan10

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.databinding.ActivityPermohonanSuratBinding
import com.google.android.material.tabs.TabLayoutMediator

class PermohonanSuratActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermohonanSuratBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermohonanSuratBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Layanan Surat Desa"

        // 2. Setup ViewPager2 Adapter
        val adapter = PermohonanSuratAdapter(this)
        binding.viewPager.adapter = adapter

        // 3. Hubungkan TabLayout dengan ViewPager2 (Modul P10)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Jenis Surat"
                1 -> "Form Pengajuan"
                2 -> "Riwayat Status"
                else -> "Tab"
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}