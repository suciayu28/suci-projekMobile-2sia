package com.example.suci_loyalty.complain

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.suci_loyalty.R
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.data.entity.ComplaintEntity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class ComplaintFormActivity : AppCompatActivity() {

    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var btnSaveComplaint: MaterialButton
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_form)

        // Inisialisasi Database
        db = AppDatabase.getInstance(this)

        // Inisialisasi Komponen UI
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        btnSaveComplaint = findViewById(R.id.btnSaveComplaint)

        btnSaveComplaint.setOnClickListener {
            saveComplaintData()
        }
    }

    private fun saveComplaintData() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom laporan!", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val complaint = ComplaintEntity(
                title = title,
                description = description
            )

            db.complaintDao().insert(complaint)

            Toast.makeText(this@ComplaintFormActivity, "Pengaduan berhasil dikirim!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}