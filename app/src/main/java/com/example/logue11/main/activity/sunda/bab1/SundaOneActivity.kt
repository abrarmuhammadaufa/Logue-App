package com.example.logue11.main.activity.sunda.bab1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivitySundaOneBinding
import com.example.logue11.main.activity.dictionary.DictionaryActivity
import com.example.logue11.main.activity.speaking.SpeakingActivity

class SundaOneActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySundaOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySundaOneBinding.inflate(LayoutInflater.from(this))

        supportActionBar?.title=("Bab 1: Mengenal Keluarga")

        setContentView(binding.root)

        binding.cdDictionary.setOnClickListener {
            startActivity(Intent(this@SundaOneActivity, DictionaryActivity::class.java))
        }

        binding.cdVoiceGames.setOnClickListener {
            startActivity(Intent(this@SundaOneActivity, SpeakingActivity::class.java))
        }

    }
}