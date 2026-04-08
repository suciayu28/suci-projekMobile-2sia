package com.example.suci_loyalty.pertemuan4

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.R
import kotlin.math.PI
import kotlin.math.pow

class RumusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("LIFECYCLE", "RumusActivity dibuat pertama kali")

        setContentView(R.layout.activity_rumus)

        // 🔹 Ambil title & desc
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")

        findViewById<TextView>(R.id.tvTitle).text = title
        findViewById<TextView>(R.id.tvDesc).text = desc

        // 🔹 SEGITIGA
        val alas = findViewById<EditText>(R.id.alas)
        val tinggi = findViewById<EditText>(R.id.tinggi)
        val btnSegitiga = findViewById<Button>(R.id.btnSegitiga)
        val hasilSegitiga = findViewById<TextView>(R.id.hasilSegitiga)

        btnSegitiga.setOnClickListener {

            if (alas.text.toString().isEmpty() || tinggi.text.toString().isEmpty()) {
                Toast.makeText(this, "Isi semua data!", Toast.LENGTH_SHORT).show()
            } else {
                val a = alas.text.toString().toDouble()
                val t = tinggi.text.toString().toDouble()

                val luas = 0.5 * a * t
                hasilSegitiga.text = "Hasil: %.2f".format(luas)
            }
        }

        // 🔹 BOLA
        val jari = findViewById<EditText>(R.id.jari)
        val btnBola = findViewById<Button>(R.id.btnBola)
        val hasilBola = findViewById<TextView>(R.id.hasilBola)

        btnBola.setOnClickListener {

            if (jari.text.toString().isEmpty()) {
                Toast.makeText(this, "Isi jari-jari!", Toast.LENGTH_SHORT).show()
            } else {
                val r = jari.text.toString().toDouble()

                val volume = 4.0 / 3.0 * PI * r.pow(3)
                hasilBola.text = "Hasil: %.2f".format(volume)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e("LIFECYCLE", "RumusActivity terlihat di layar")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("LIFECYCLE", "RumusActivity dihapus dari stack")
    }
}