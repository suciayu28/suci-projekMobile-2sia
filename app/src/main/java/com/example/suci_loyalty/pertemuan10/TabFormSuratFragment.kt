package com.example.suci_loyalty.pertemuan10

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.suci_loyalty.R
// Ganti import ini agar sesuai dengan nama file XML Anda
import com.example.suci_loyalty.databinding.ActivityTabFormSuratFragmentBinding

class TabFormSuratFragment : Fragment(R.layout.activity_tab_form_surat_fragment) {
    // Gunakan class binding yang dihasilkan dari nama file XML Anda
    private var _binding: ActivityTabFormSuratFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Lakukan binding
        _binding = ActivityTabFormSuratFragmentBinding.bind(view)

        binding.btnSubmitPermohonan.setOnClickListener {
            // Logika submit Anda di sini
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}