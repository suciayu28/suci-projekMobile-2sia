package com.example.suci_loyalty.pertemuan3

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suci_loyalty.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Setup Dropdown Agama (Soal 1)
        val listAgama = arrayOf("Islam", "Kristen", "Katolik", "Hindu", "Budha", "Konghucu")
        val adapterAgama = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listAgama)
        binding.spinnerAgama.adapter = adapterAgama

        // 2. Setup DatePicker (Soal 1)
        binding.etTanggalLahir.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(this, { _, year, month, day ->
                binding.etTanggalLahir.setText("$day/${month + 1}/$year")
                binding.etTanggalLahir.error = null
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 3. Tombol Submit
        binding.btnRegisterSubmit.setOnClickListener {
            performRegistration()
        }
    }

    private fun performRegistration() {
        val nama = binding.etNama.text.toString().trim()
        val tglLahir = binding.etTanggalLahir.text.toString().trim()
        val username = binding.etRegUsername.text.toString().trim()
        val password = binding.etRegPassword.text.toString().trim()
        val confirmPass = binding.etConfirmPassword.text.toString().trim()
        val selectedGenderId = binding.rgGender.checkedRadioButtonId

        var isValid = true

        // --- VALIDASI SOAL 2 (Tanpa Toast/Dialog) ---
        if (nama.isEmpty()) {
            binding.etNama.error = "Nama harus diisi"
            isValid = false
        }
        if (tglLahir.isEmpty()) {
            binding.etTanggalLahir.error = "Tanggal lahir harus diisi"
            isValid = false
        }
        if (selectedGenderId == -1) {
            // RadioButton tidak punya .error, pakai Toast khusus ini diperbolehkan
            // atau beri error pada container terdekat.
            Toast.makeText(this, "Pilih Jenis Kelamin!", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        if (username.isEmpty()) {
            binding.etRegUsername.error = "Username harus diisi"
            isValid = false
        }
        if (password.isEmpty()) {
            binding.etRegPassword.error = "Password harus diisi"
            isValid = false
        }
        if (confirmPass != password) {
            binding.etConfirmPassword.error = "Password tidak cocok!"
            isValid = false
        }

        // --- SIMPAN DATA SOAL 3 ---
        if (isValid) {
            val sharedPref = getSharedPreferences("user_pref", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()

            editor.putString("reg_username", username)
            editor.putString("reg_password", password)
            editor.putString("reg_nama", nama)
            editor.apply()

            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
            finish() // Tutup halaman dan kembali ke Login
        }
    }
}