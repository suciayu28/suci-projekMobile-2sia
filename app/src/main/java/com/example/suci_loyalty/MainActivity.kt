package com.example.suci_loyalty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.math.PI
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menghubungkan komponen UI dengan kode Kotlin
        val alas = findViewById<EditText>(R.id.alas)
        val tinggi = findViewById<EditText>(R.id.tinggi)
        val btnSegitiga = findViewById<Button>(R.id.btnSegitiga)
        val hasilSegitiga = findViewById<TextView>(R.id.hasilSegitiga)

        val jari = findViewById<EditText>(R.id.jari)
        val btnBola = findViewById<Button>(R.id.btnBola)
        val hasilBola = findViewById<TextView>(R.id.hasilBola)

        // Event ketika tombol Hitung Luas Segitiga ditekan
        btnSegitiga.setOnClickListener {

            if (alas.text.toString().isEmpty() || tinggi.text.toString().isEmpty()) {

                Toast.makeText(this, "Masukkan alas dan tinggi terlebih dahulu", Toast.LENGTH_SHORT).show()

            } else {

                val a = alas.text.toString().toDouble()
                val t = tinggi.text.toString().toDouble()

                val luas = 0.5 * a * t

                val hasil = String.format("%.2f", luas)

                hasilSegitiga.text = "Luas Segitiga = $hasil cm²"
            }
        }

        // Event ketika tombol Hitung Volume Bola ditekan
        btnBola.setOnClickListener {

            if (jari.text.toString().isEmpty()) {

                Toast.makeText(this, "Masukkan jari-jari terlebih dahulu", Toast.LENGTH_SHORT).show()

            } else {

                val r = jari.text.toString().toDouble()

                val volume = 4.0 / 3.0 * PI * r.pow(3)

                val hasil = String.format("%.2f", volume)

                hasilBola.text = "Volume Bola = $hasil cm³"
            }
        }
    }
}