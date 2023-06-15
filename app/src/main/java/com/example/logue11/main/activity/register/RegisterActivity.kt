package com.example.logue11.main.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import com.example.logue11.R
import com.example.logue11.databinding.ActivityRegisterBinding
import com.example.logue11.main.activity.login.LoginActivity
import com.example.logue11.main.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))

        supportActionBar?.hide()

        setContentView(binding.root)

        binding.ivPasswordToggle.setOnClickListener {
            val passwordToggle = binding.edRegPassword.inputType

            if (passwordToggle == InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT) {
                binding.edRegPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_open_password)
            } else {
                binding.edRegPassword.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                binding.ivPasswordToggle.setImageResource(R.drawable.ic_hide_password)
            }
        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.tvIntentLogin.setOnClickListener {
            Toast.makeText(this@RegisterActivity, "Berpindah ke laman masuk", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun register() {

        val emailcheck = binding.edRegEmail.text.toString()

        if (binding.edRegFullname.text.isEmpty() || binding.edRegFullname.text.length > 20){
            binding.edRegFullname.error = "Mohon isi nama lengkap"
            binding.edRegFullname.requestFocus()
            return

        }else if(binding.edRegUsername.text.isEmpty()){
            binding.edRegUsername.error = "Mohon isi username"
            binding.edRegUsername.requestFocus()
            return

        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailcheck).matches()){
            binding.edRegEmail.error = "Alamat email tidak valid"
            binding.edRegEmail.requestFocus()
            return

        }else if (binding.edRegPassword.text.length < 8){
            binding.edRegPassword.error = "Isi kata sandi minimal 8 karakter"
            binding.edRegPassword.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit.register(
            binding.edRegFullname.text.toString(),
            binding.edRegUsername.text.toString(),
            binding.edRegEmail.text.toString(),
            binding.edRegPassword.text.toString())
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@RegisterActivity, "Berhasil membuat akun", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                    } else{
                        Toast.makeText(this@RegisterActivity, "Gagal membuat akun", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<RegisterResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })

    }



}