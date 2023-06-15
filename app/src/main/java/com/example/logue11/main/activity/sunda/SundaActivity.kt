package com.example.logue11.main.activity.sunda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivitySundaBinding

class SundaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySundaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySundaBinding.inflate(LayoutInflater.from(this))

        supportActionBar?.hide()

        setContentView(binding.root)
    }
}