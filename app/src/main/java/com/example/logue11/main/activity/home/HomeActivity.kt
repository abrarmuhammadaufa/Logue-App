package com.example.logue11.main.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.logue11.databinding.ActivityHomeBinding
import com.example.logue11.main.activity.profile.ProfileActivity
import com.example.logue11.main.activity.sunda.SundaActivity
import com.example.logue11.main.sharedpreferences.UserPreference

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        userPreference = UserPreference(this)

        supportActionBar?.hide()

        setContentView(binding.root)

        getUsername()

        binding.ivProfile.setOnClickListener {
            startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
        }

        binding.ivSundaForward.setOnClickListener {
            startActivity(Intent(this@HomeActivity, SundaActivity::class.java))
        }

        binding.ivJawaForward.setOnClickListener {
            startActivity(Intent(this@HomeActivity, SundaActivity::class.java))
        }

    }

    private fun getUsername() {
        binding.tvUsername.text = userPreference.getFullName()
    }
}