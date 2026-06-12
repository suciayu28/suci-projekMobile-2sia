package com.example.suci_loyalty.note

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.data.entity.NoteEntity
import com.example.suci_loyalty.databinding.ActivityNoteFormBinding
import kotlinx.coroutines.launch

class NoteFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteFormBinding
    // 1. Deklarasikan variabel db sesuai modul (Langkah 4)
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2. Inisialisasi DB untuk memanggil noteDao().insert() sesuai modul
        db = AppDatabase.getInstance(this)

        // Tombol back untuk menutup activity jika batal mengisi form
        binding.btnBack.setOnClickListener {
            finish()
        }

        // 3. Logika saat tombol Save diklik sesuai gambar modul
        binding.btnSaveNote.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val content = binding.etContent.text.toString().trim()

            // Cek jika title dan content tidak kosong
            if (title.isNotBlank() && content.isNotBlank()) {
                // Penggunaan Coroutine dalam melakukan insert data
                lifecycleScope.launch {
                    val note = NoteEntity(
                        title = title,
                        content = content,
                        createdAt = System.currentTimeMillis()
                    )
                    db.noteDao().insert(note)

                    Toast.makeText(this@NoteFormActivity, "Catatan berhasil disimpan!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Isi semua kolom!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}