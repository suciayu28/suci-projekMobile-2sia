package com.example.suci_loyalty.pertemuan7

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.suci_loyalty.R
import com.example.suci_loyalty.complain.ComplaintFragment
import com.example.suci_loyalty.databinding.ActivityBaseBinding

class baseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> replaceFragment(HomeFragment())
                R.id.menu_about -> replaceFragment(AboutFragment())
                R.id.menu_profile -> replaceFragment(ProfileFragment())
                R.id.note -> replaceFragment(com.example.suci_loyalty.note.NoteFragment())

                // ➔ Cuma menyelipkan menu complain di sini untuk membuka halaman form pengaduan
                R.id.complain -> replaceFragment(ComplaintFragment())


                else -> false
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}