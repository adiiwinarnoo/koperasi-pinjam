package com.pinjamankoperasi.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pinjamankoperasi.R
import com.pinjamankoperasi.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnPinjaman.setOnClickListener {
            startActivity(Intent(this,PinjamanActivity::class.java))
        }

        binding.btnUploadStock.setOnClickListener {
            startActivity(Intent(this,UploadProductActivity::class.java))
        }

        binding.btnStock.setOnClickListener {
            startActivity(Intent(this,StockProductActivity::class.java))
        }

        binding.btnListPinjaman.setOnClickListener {
            startActivity(Intent(this,ListPinjamanActivity::class.java))
        }

    }
}