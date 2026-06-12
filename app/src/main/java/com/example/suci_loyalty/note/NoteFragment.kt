package com.example.suci_loyalty.note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.data.entity.NoteEntity
import com.example.suci_loyalty.databinding.FragmentNoteBinding
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NoteAdapter
    private lateinit var db: AppDatabase
    private val notes = mutableListOf<NoteEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Database
        db = AppDatabase.getInstance(requireContext())

        // 🛠️ PERBAIKAN: Menambahkan parameter `this` ke NoteAdapter sesuai petunjuk modul
        adapter = NoteAdapter(notes, this)

        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.adapter = adapter

        // Garis pemisah antar item catatan
        val dividerItemDecoration = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.rvNotes.addItemDecoration(dividerItemDecoration)

        // Panggil fungsi untuk mengambil data dari database
        fetchNotes()

        // 🛠️ TOMBOL BACK BARU: Mengembalikan halaman/menutup Fragment/Activity
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // Logika ketika tombol FloatingActionButton (+) diklik
        binding.fabAddNote.setOnClickListener {
            // Membuka form Input Database Room Anda
            val intent = Intent(requireContext(), NoteFormActivity::class.java)
            startActivity(intent)
        }
    }

    // Mengambil seluruh data catatan secara asinkron (Coroutine)
    private fun fetchNotes() {
        lifecycleScope.launch {
            val data = db.noteDao().getAll() // Pemanggilan query
            notes.clear()
            notes.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    // 🛠️ FUNGSI BARU: Menghapus catatan menggunakan Coroutine sesuai modul
    fun deleteNote(note: NoteEntity) {
        lifecycleScope.launch {
            db.noteDao().delete(note) // Hapus Note
            fetchNotes() // Fetch lagi data notes terbaru
        }
    }

    override fun onResume() {
        super.onResume()
        // Otomatis refresh list data saat kembali dari NoteFormActivity
        fetchNotes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}