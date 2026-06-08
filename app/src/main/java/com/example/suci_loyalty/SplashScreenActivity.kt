package com.example.suci_loyalty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.suci_loyalty.onboarding.OnboardingActivity // 🆕 Import OnboardingActivity baru
import com.example.suci_loyalty.pertemuan3.LoginActivity
import com.example.suci_loyalty.pertemuan7.baseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        val mainView = findViewById<android.view.View>(R.id.main)
        mainView?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        lifecycleScope.launch {
            delay(2000)

            // 🛠️ PERBAIKAN UTAMA: Disamakan menjadi "user_pref" agar sinkron dengan wadah LoginActivity kamu
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            // Jika sudah login masuk ke baseActivity, jika belum login arahkan ke OnboardingActivity dulu
            val intent = if (isLogin) {
                Intent(this@SplashScreenActivity, baseActivity::class.java)
            } else {
                // Diubah dari LoginActivity ke OnboardingActivity agar slide onboarding muncul saat pertama kali aplikasi dibuka
                Intent(this@SplashScreenActivity, OnboardingActivity::class.java)
            }

            startActivity(intent)
            finish()
        }
    }
}