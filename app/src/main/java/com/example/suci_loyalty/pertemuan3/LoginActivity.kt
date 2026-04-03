package com.example.suci_loyalty.pertemuan3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.suci_loyalty.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔥 TARO DI SINI
        binding.btnLogin.setOnClickListener {

            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).duration = 100
            }

            val username = binding.etUsername.text.toString()

            val intent = Intent(this, WelcomeActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }
    }
}