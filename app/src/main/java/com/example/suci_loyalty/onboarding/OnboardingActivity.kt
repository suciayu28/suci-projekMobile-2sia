package com.example.suci_loyalty.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.suci_loyalty.R
import com.example.suci_loyalty.databinding.ActivityOnboardingBinding
import com.example.suci_loyalty.pertemuan3.LoginActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragments = listOf(
            Onboarding1Fragment(),
            Onboarding2Fragment(),
            Onboarding3Fragment()
        )

        val adapter = OnboardingFragmentAdapter(this, fragments)
        binding.onboardingViewPager.adapter = adapter
        binding.dotIndicator.attachTo(binding.onboardingViewPager)

        binding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 2) {
                    val btnStart = findViewById<Button>(R.id.btnStart)
                    btnStart?.setOnClickListener {

                        val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })
    }
}