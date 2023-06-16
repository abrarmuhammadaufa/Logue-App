package com.example.logue11.main.activity.sunda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.logue11.databinding.ActivitySundaBinding
import com.example.logue11.main.activity.sunda.bab1.SundaOneActivity

class SundaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySundaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySundaBinding.inflate(LayoutInflater.from(this))

        supportActionBar?.title=("Basa Sunda")

        setContentView(binding.root)

        binding.btnMulai1.setOnClickListener {
            startActivity(Intent(this@SundaActivity, SundaOneActivity::class.java))
        }

    }
}