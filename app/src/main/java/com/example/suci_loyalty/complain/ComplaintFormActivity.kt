package com.example.suci_loyalty.complain

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.suci_loyalty.R
import com.example.suci_loyalty.data.AppDatabase
import com.example.suci_loyalty.data.entity.ComplaintEntity
import com.example.suci_loyalty.pertemuan10.PermohonanSuratActivity
import com.example.suci_loyalty.utils.NotificationHelper
import com.example.suci_loyalty.utils.PermissionHelper
import com.example.suci_loyalty.utils.ReminderHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.util.Calendar

class ComplaintFormActivity : AppCompatActivity() {

    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etReminderMinutes: TextInputEditText
    private lateinit var btnSaveComplaint: MaterialButton
    private lateinit var db: AppDatabase

    // Launcher Izin Notifikasi demi mendukung Android 13 ke atas
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Izin notifikasi ditolak. Anda tidak akan menerima pengingat.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_form)

        // Cek dan Minta Izin Akses Notifikasi
        if (PermissionHelper.isNotificationPermissionRequired()) {
            val permission = Manifest.permission.POST_NOTIFICATIONS
            if (!PermissionHelper.hasPermission(this, permission)) {
                PermissionHelper.requestPermission(notificationPermissionLauncher, permission)
            }
        }

        // Inisialisasi Database
        db = AppDatabase.getInstance(this)

        // Inisialisasi Komponen UI
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etReminderMinutes = findViewById(R.id.etReminderMinutes)
        btnSaveComplaint = findViewById(R.id.btnSaveComplaint)

        btnSaveComplaint.setOnClickListener {
            saveComplaintData()
        }
    }

    private fun saveComplaintData() {
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val minutesStr = etReminderMinutes.text.toString().trim()

        if (title.isEmpty() || description.isEmpty() || minutesStr.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom laporan dan menit pengingat!", Toast.LENGTH_SHORT).show()
            return
        }

        val reminderMinutes = minutesStr.toIntOrNull() ?: 1

        lifecycleScope.launch {
            val complaint = ComplaintEntity(
                title = title,
                description = description
            )

            db.complaintDao().insert(complaint)

            // ---- IMPLEMENTASI LOCAL NOTIFICATION (TUGAS DOSEN) ----
            // Saat diklik, mengarah kembali ke halaman pelacakan/surat layanan desa yang relevan
            val intentDestination = Intent(this@ComplaintFormActivity, PermohonanSuratActivity::class.java)

            // 1. Kirim Local Notification Instan bahwa laporan sukses
            NotificationHelper.showNotification(
                this@ComplaintFormActivity,
                "Bina Desa - Pengaduan Sukses",
                "Laporan \"$title\" telah sukses dikirim ke sistem admin desa.",
                intentDestination
            )

            // 2. Set Fitur Reminder Dinamis berdasarkan Menit Input Pengguna
            val calendar = Calendar.getInstance().apply {
                add(Calendar.MINUTE, reminderMinutes)
            }

            ReminderHelper.setReminder(
                context = this@ComplaintFormActivity,
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minute = calendar.get(Calendar.MINUTE),
                title = "Evaluasi Pengaduan Desa",
                message = "Sudah $reminderMinutes menit berlalu. Silakan cek status tindak lanjut pengaduan Anda.",
                targetActivity = PermohonanSuratActivity::class.java
            )

            Toast.makeText(
                this@ComplaintFormActivity,
                "Pengaduan dikirim! Pengingat diset $reminderMinutes menit lagi.",
                Toast.LENGTH_LONG
            ).show()

            finish()
        }
    }
}