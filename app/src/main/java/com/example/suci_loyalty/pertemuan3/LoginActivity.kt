package com.example.suci_loyalty.pertemuan3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.databinding.ActivityLoginBinding
import com.example.suci_loyalty.pertemuan4.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("LIFECYCLE", "LoginActivity dibuat pertama kali")

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            it.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
                it.animate().scaleX(1f).scaleY(1f).duration = 100
            }

            val username = binding.etUsername.text.toString()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)

            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("LIFECYCLE", "LoginActivity terlihat di layar")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("LIFECYCLE", "LoginActivity dihapus dari stack")
    }
}