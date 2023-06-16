package com.example.logue11.main.activity.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivityProfileBinding
import com.example.logue11.main.activity.tba.OnProgressActivity
import com.example.logue11.main.activity.welcome.WelcomeActivity
import com.example.logue11.main.sharedpreferences.UserPreference

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(LayoutInflater.from(this))
        userPreference = UserPreference(this)

        supportActionBar?.hide()

        setContentView(binding.root)

        getProfile()

        val comingSoon = Intent(this, OnProgressActivity::class.java)

        binding.llEditProfile.setOnClickListener {
            startActivity(comingSoon)
            finish()
        }

        binding.llEditPassword.setOnClickListener {
            startActivity(comingSoon)
            finish()
        }

        binding.llNotification.setOnClickListener {
            startActivity(comingSoon)
            finish()
        }

        binding.llAboutUs.setOnClickListener {
            startActivity(comingSoon)
            finish()
        }

        binding.btnLogout.setOnClickListener {
            userPreference.removeUser()
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getProfile() {

        val username = userPreference.getUsername()

        binding.tvUsername.text = "@${username}"
        binding.tvFullname.text = userPreference.getFullName()
        binding.tvEmail.text = userPreference.getEmail()
    }

}