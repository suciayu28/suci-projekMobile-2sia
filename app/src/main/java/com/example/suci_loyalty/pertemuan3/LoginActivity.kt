package com.example.suci_loyalty.pertemuan3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.databinding.ActivityLoginBinding
// Ganti import MainActivity menjadi baseActivity
import com.example.suci_loyalty.pertemuan7.baseActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        // Kondisi jika isLogin bernilai true
        val isLogin = sharedPref.getBoolean("isLogin", false)
        if (isLogin) {
            // PERBAIKAN 1: Arahkan ke baseActivity
            startActivity(Intent(this, baseActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).duration = 100
            }

            val usernameInput = binding.etUsername.text.toString().trim()
            val passwordInput = binding.etPassword.text.toString().trim()

            if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(this, "Username dan Password wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usernameInput == passwordInput) {
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", usernameInput)
                editor.apply()

                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

                // PERBAIKAN 2: Arahkan ke baseActivity (Bottom Navigation)
                startActivity(Intent(this, baseActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Username dan Password harus sama!", Toast.LENGTH_LONG).show()
                binding.etPassword.text?.clear()
            }
        }
    }
}