package com.example.logue11.main.activity.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivityWelcomeBinding
import com.example.logue11.main.activity.home.HomeActivity
import com.example.logue11.main.activity.login.LoginActivity
import com.example.logue11.main.activity.register.RegisterActivity
import com.example.logue11.main.sharedpreferences.UserPreference

@Suppress("DEPRECATION")
class WelcomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityWelcomeBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this))

        supportActionBar?.hide()

        setContentView(binding.root)

        userPreference = UserPreference(this)

        if(userPreference.loginStatus()){
            startActivity(Intent(this@WelcomeActivity, HomeActivity::class.java))
        }

        binding.btnLogin.setOnClickListener{
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        }

        binding.btnRegis.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }
}