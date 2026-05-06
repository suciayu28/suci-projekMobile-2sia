package com.example.suci_loyalty.pertemuan3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.databinding.ActivityLoginBinding
// Import baseActivity sesuai struktur folder kamu
import com.example.suci_loyalty.pertemuan7.baseActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Kondisi jika isLogin bernilai true (Auto Login)
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            startActivity(Intent(this, baseActivity::class.java))
            finish()
        }

        // --- PERBAIKAN: Aktifkan Klik ke Halaman Register ---
        binding.btnToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            // Animasi tombol
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).duration = 100
            }

            val usernameInput = binding.etUsername.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            // Validasi Input Kosong (Soal 2: Tampilan error tanpa Toast)
            if (usernameInput.isEmpty()) {
                binding.etUsername.error = "Username wajib diisi!"
                return@setOnClickListener
            }
            if (passwordInput.isEmpty()) {
                binding.etPassword.error = "Password wajib diisi!"
                return@setOnClickListener
            }

            // --- SOAL 3: Ambil data dari SharedPreferences hasil registrasi ---
            val registeredUser = sharedPref.getString("reg_username", null)
            val registeredPass = sharedPref.getString("reg_password", null)

            // Rule 1: Username == Password
            val rule1 = (usernameInput == passwordInput)

            // Rule 2: Cocok dengan data SharedPreferences (Ketentuan Soal 3)
            val rule2 = (usernameInput == registeredUser && passwordInput == registeredPass)

            if (rule1 || rule2) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", usernameInput)
                editor.apply()

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                // Arahkan ke baseActivity
                startActivity(Intent(this, baseActivity::class.java))
                finish()
            } else {
                // Tampilkan error jika tidak memenuhi kedua rule
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_LONG).show()
                binding.etPassword.text?.clear()
            }
        }
    }
}