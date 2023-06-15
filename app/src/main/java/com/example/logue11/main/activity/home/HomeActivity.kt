package com.example.logue11.main.activity.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivityHomeBinding
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
    }

    fun getUsername() {
        binding.tvUsername.text = userPreference.getFullName()
    }

}