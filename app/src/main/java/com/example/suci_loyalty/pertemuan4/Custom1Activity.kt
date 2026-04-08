package com.example.suci_loyalty.pertemuan4

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.R

class Custom1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom1)

        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")

        findViewById<TextView>(R.id.tvTitle).text = title
        findViewById<TextView>(R.id.tvDesc).text = desc
    }
}