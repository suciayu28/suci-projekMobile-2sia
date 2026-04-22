package com.example.suci_loyalty.pertemuan4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.suci_loyalty.R
import com.example.suci_loyalty.pertemuan3.LoginActivity
import com.example.suci_loyalty.pertemuan6.WebViewActivity // Import dari package pertemuan 6
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("LIFECYCLE", "MainActivity dibuat")
        setContentView(R.layout.activity_main)

        // 1. SETUP TOOLBAR (Implementasi Pertemuan 5)
        // Menjadikan widget Toolbar di XML sebagai ActionBar resmi
        val toolbar = findViewById<Toolbar>(R.id.toolbarMain)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Dashboard Bina Desa"

        // 2. INISIALISASI WIDGET
        val btnWebView = findViewById<Button>(R.id.btnWebView)
        val btnRumus = findViewById<View>(R.id.btnRumus)
        val btnCustom1 = findViewById<View>(R.id.btnCustom1)
        val btnCustom2 = findViewById<View>(R.id.btnCustom2)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // 3. LOGIKA NAVIGASI WEBVIEW (Pertemuan 6)
        btnWebView?.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

        // 4. LOGIKA NAVIGASI RUMUS & CUSTOM (Pertemuan 4)
        btnRumus?.setOnClickListener {
            val intent = Intent(this, RumusActivity::class.java)
            intent.putExtra("title", "Rumus Bangun")
            startActivity(intent)
        }

        btnCustom1?.setOnClickListener {
            val intent = Intent(this, Custom1Activity::class.java)
            intent.putExtra("title", "Custom 1")
            intent.putExtra("desc", "Halaman pertama layanan")
            startActivity(intent)
        }

        btnCustom2?.setOnClickListener {
            val intent = Intent(this, Custom2Activity::class.java)
            intent.putExtra("title", "Custom 2")
            intent.putExtra("desc", "Halaman kedua layanan")
            startActivity(intent)
        }

        // 5. LOGIKA LOGOUT & CLEAR SESSION (Sesuai Gambar 3 di Modul)
        btnLogout?.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi Keluar")
                .setMessage("Apakah Anda yakin ingin logout dari aplikasi Bina Desa?")
                .setPositiveButton("Ya") { dialog, _ ->

                    // PERBAIKAN: Nama harus "user_pref" agar sama dengan LoginActivity
                    val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()

                    // Sesuai Gambar 3: Hapus semua data session
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()

                    // Pindah ke LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    // Flags ini penting agar user tidak bisa tekan tombol 'Back' kembali ke dashboard
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("LIFECYCLE", "MainActivity terlihat (onStart)")
    }

    override fun onResume() {
        super.onResume()
        Log.e("LIFECYCLE", "MainActivity berinteraksi (onResume)")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("LIFECYCLE", "MainActivity dihancurkan (onDestroy)")
    }
}