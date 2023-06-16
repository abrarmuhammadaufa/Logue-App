package com.example.logue11.main.activity.speaking

import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivitySpeakingBinding

class SpeakingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySpeakingBinding
    private lateinit var mediaRecorder: MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivitySpeakingBinding.inflate(LayoutInflater.from(this))

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}