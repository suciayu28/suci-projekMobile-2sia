package com.example.suci_loyalty.pertemuan3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.suci_loyalty.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")

        binding.tvSelamatDatang.text = "Welcome, $username 🎉"    }
}