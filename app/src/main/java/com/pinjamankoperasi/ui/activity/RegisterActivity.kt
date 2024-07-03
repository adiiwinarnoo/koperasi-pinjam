package com.pinjamankoperasi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.pinjamankoperasi.R
import com.pinjamankoperasi.databinding.ActivityRegisterBinding
import com.pinjamankoperasi.ui.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var registerViewModel : RegisterViewModel
    var nik: String? = null
    var nama : String? = null
    var jabatan: String? = null
    var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSimpan.setOnClickListener {
            binding.idProgressbar.visibility = View.VISIBLE
            checkedData()
        }

        registerViewModel.registerResult.observe(this){
            if (it.status == 1){
                binding.idProgressbar.visibility = View.GONE
                Toast.makeText(this, "Register Sukses", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                binding.idProgressbar.visibility = View.GONE
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun checkedData(){
        nik = binding.edtNik.text.toString().trim()
        nama = binding.edtNamaKaryawan.text.toString().trim()
        jabatan = binding.edtJabatan.text.toString().trim()
        password = binding.edtPassword.text.toString().trim()

        if (nik.isNullOrEmpty()){
            binding.idProgressbar.visibility = View.GONE
            binding.edtNik.setError("data tidak boleh kosong")
        }
        else if (nama.isNullOrEmpty()) {
            binding.idProgressbar.visibility = View.GONE
            binding.edtNamaKaryawan.setError("data tidak boleh kosong")
        }
        else if (jabatan.isNullOrEmpty()) {
            binding.idProgressbar.visibility = View.GONE
            binding.edtJabatan.setError("data tidak boleh kosong")
        }
        else if (password.isNullOrEmpty()) {
            binding.idProgressbar.visibility = View.GONE
            binding.edtPassword.setError("data tidak boleh kosong")
        }
        else registerViewModel.register(nik!!,nama!!, jabatan!!, password!!)
    }
}