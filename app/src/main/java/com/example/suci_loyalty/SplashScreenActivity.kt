package com.example.suci_loyalty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.suci_loyalty.pertemuan3.LoginActivity
import com.example.suci_loyalty.pertemuan4.MainActivity // Import MainActivity dari pertemuan 4
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        // Handler Window Insets (Agar Splash Full Screen/Edge-to-Edge)
        val mainView = findViewById<android.view.View>(R.id.main)
        mainView?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        // --- LOGIKA PERPINDAHAN HALAMAN ---
        lifecycleScope.launch {
            // 1. Delay splash screen selama 2 detik
            delay(2000)

            // 2. Cek SharedPreferences (kunci "isLogin" harus sama dengan di LoginActivity)
            val sharedPref = getSharedPreferences("session_user", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            // --- LOGIKA PERPINDAHAN HALAMAN ---
            lifecycleScope.launch {
                // 1. Delay splash screen selama 2 detik
                delay(2000)

                // 2. Jika ingin DIPAKSA selalu muncul Login setelah Splash:
                // Langsung arahkan ke LoginActivity tanpa mengecek boolean isLogin
                val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
                startActivity(intent)

                // 3. Tutup Splash
                finish()
            }
        }
    }
}