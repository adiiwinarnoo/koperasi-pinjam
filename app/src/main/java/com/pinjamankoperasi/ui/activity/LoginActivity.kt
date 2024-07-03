package com.pinjamankoperasi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.pinjamankoperasi.R
import com.pinjamankoperasi.databinding.ActivityLoginBinding
import com.pinjamankoperasi.ui.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener {
            binding.idProgressbar.visibility = View.VISIBLE
            checkedData()
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        loginViewModel.loginResult.observe(this) { response ->
            when(response.message) {
                "Nomor Induk Tidak Terdaftar" -> {
                    Toast.makeText(this, "${response.message}", Toast.LENGTH_SHORT).show()
                    binding.idProgressbar.visibility = View.GONE
                }
                "Login Berhasil" -> {
                    binding.idProgressbar.visibility = View.GONE
                    startActivity(Intent(this,HomeActivity::class.java))
                }
                "Password Tidak Sesuai" -> {
                    Toast.makeText(this, "${response.message}", Toast.LENGTH_SHORT).show()
                    binding.idProgressbar.visibility = View.GONE
                }
            }
        }
        loginViewModel.errorResult.observe(this){
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkedData() {
        var nomorInduk = binding.edtNomorIndukk.text.toString()
        var password = binding.edtPassword.text.toString()

        if (nomorInduk.isNullOrEmpty()) {
            Toast.makeText(this, "Nomor Induk tidak boleh kosong", Toast.LENGTH_SHORT).show()
            binding.idProgressbar.visibility = View.GONE
        } else if (password.isNullOrEmpty()) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            binding.idProgressbar.visibility = View.GONE
        }else {
            loginViewModel.login(nomorInduk,password)
        }
    }
}