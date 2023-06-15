package com.example.logue11.main.activity.tba

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivityOnProgressBinding

class OnProgressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnProgressBinding.inflate(LayoutInflater.from(this))

        supportActionBar?.hide()

        setContentView(binding.root)
    }
}