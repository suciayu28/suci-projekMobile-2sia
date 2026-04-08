package com.example.suci_loyalty.pertemuan4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.R
import com.example.suci_loyalty.pertemuan3.LoginActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("LIFECYCLE", "MainActivity dibuat pertama kali")

        setContentView(R.layout.activity_main)

        val btnRumus = findViewById<View>(R.id.btnRumus)
        val btnCustom1 = findViewById<View>(R.id.btnCustom1)
        val btnCustom2 = findViewById<View>(R.id.btnCustom2)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnRumus?.setOnClickListener {
            val intent = Intent(this, RumusActivity::class.java)
            intent.putExtra("title", "Rumus Bangun")
            startActivity(intent)
        }

        btnCustom1.setOnClickListener {
            val intent = Intent(this, Custom1Activity::class.java)
            intent.putExtra("title", "Custom 1")
            intent.putExtra("desc", "Halaman pertama")
            startActivity(intent)
        }

        btnCustom2.setOnClickListener {
            val intent = Intent(this, Custom2Activity::class.java)
            intent.putExtra("title", "Custom 2")
            intent.putExtra("desc", "Halaman kedua")
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Konfirmasi")
                .setMessage("Yakin ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Tidak", null)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("LIFECYCLE", "MainActivity terlihat di layar")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("LIFECYCLE", "MainActivity dihapus dari stack")
    }
}