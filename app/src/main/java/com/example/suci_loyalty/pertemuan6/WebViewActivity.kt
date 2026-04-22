package com.example.suci_loyalty.pertemuan6

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.suci_loyalty.R

class WebViewActivity : AppCompatActivity() {

    // Gunakan lazy atau dideklarasikan di awal agar bisa diakses di onBackPressed tanpa findViewById lagi
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // 1. Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbarWeb)
        setSupportActionBar(toolbar)

        // Aktifkan tombol back di toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Layanan Bina Desa" // Opsional: Set judul di sini

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 2. Setup WebView
        webView = findViewById(R.id.webViewBinaDesa)
        webView.webViewClient = WebViewClient()

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowContentAccess = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true // Tambahan: agar konten pas dengan lebar layar

        // 3. Load URL
        webView.loadUrl("https://suci-layanan.alwaysdata.net")
    }

    // Menangani tombol back fisik/gesture HP
    // Catatan: onBackPressed() sudah deprecated di API terbaru,
    // namun untuk pembelajaran ini masih sangat umum digunakan.
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}