package com.example.suci_loyalty.pertemuan3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.databinding.ActivityLoginBinding
import com.example.suci_loyalty.pertemuan4.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Definisikan SharedPreferences (Sesuai Gambar 1 Modul)
        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Kondisi jika isLogin bernilai true (Sesuai Gambar 1 Modul)
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            // Animasi tombol
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).duration = 100
            }

            val usernameInput = binding.etUsername.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Username dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // --- PERUBAHAN LOGIKA DI SINI ---
            // Mengecek apakah input Username SAMA DENGAN input Password
            if (usernameInput == passwordInput) {

                // JIKA SAMA: Simpan sesi (Sesuai Gambar 2 Modul)
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", usernameInput)
                editor.apply()

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // JIKA BEDA: Tampilkan pesan error
                Toast.makeText(this, "Username dan Password harus sama!", Toast.LENGTH_LONG).show()
                binding.etPassword.text?.clear()
            }
        }
    }
}