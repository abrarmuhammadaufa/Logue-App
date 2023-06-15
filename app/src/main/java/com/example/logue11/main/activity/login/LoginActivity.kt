package com.example.logue11.main.activity.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.logue11.R
import com.example.logue11.databinding.ActivityLoginBinding
import com.example.logue11.main.activity.home.HomeActivity
import com.example.logue11.main.activity.register.RegisterActivity
import com.example.logue11.main.api.ApiConfig
import com.example.logue11.main.sharedpreferences.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))

        userPreference = UserPreference(this)

        supportActionBar?.hide()

        setContentView(binding.root)

        binding.ivPasswordToggle.setOnClickListener {
            val passwordToggle = binding.edLogPassword.inputType

            if (passwordToggle == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
                binding.edLogPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_open_password)
            } else {
                binding.edLogPassword.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_hide_password)
            }
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvIntentRegister.setOnClickListener {
            Toast.makeText(this@LoginActivity, "Berpindah ke laman daftar", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    fun login(){
        if(binding.edLogUsername.text.isEmpty()) {
            binding.edLogUsername.error = "Mohon isi username"
            binding.edLogUsername.requestFocus()
            return

        }else if (binding.edLogPassword.text.isEmpty()) {
            binding.edLogPassword.error = "Kata sandi tidak boleh kosong"
            binding.edLogPassword.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit.login(
            binding.edLogUsername.text.toString(),
            binding.edLogPassword.text.toString())
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if(response.isSuccessful){

                        Toast.makeText(this@LoginActivity, "Berhasil masuk", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))

                        val loginResult = response.body()!!.user
                        val token = response.body()!!.accessToken

                        if (token != null) {
                            userPreference.setUser(
                                loginResult,
                                token
                            )
                        }

                    } else{
                        Toast.makeText(this@LoginActivity, "Akun tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }
}