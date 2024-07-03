package com.pinjamankoperasi.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.pinjamankoperasi.R
import com.pinjamankoperasi.databinding.ActivityUploadProductBinding
import com.pinjamankoperasi.ui.viewmodel.UploadProductViewModel

class UploadProductActivity : AppCompatActivity() {

    lateinit var binding : ActivityUploadProductBinding
    lateinit var uploadProductViewModel : UploadProductViewModel

    var codeBarang : String? = null
    var namaProduct : String? = null
    var hargaJual = 0
    var stock = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadProductBinding.inflate(layoutInflater)
        uploadProductViewModel = ViewModelProvider(this).get(UploadProductViewModel::class.java)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSimpan.setOnClickListener {
            checkedData()
        }

        uploadProductViewModel.uploadProductResult.observe(this){
            if (it.message.equals("Success to Upload Product")){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }else{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun checkedData(){
        codeBarang = binding.edtCodeBarang.text.toString().trim()
        namaProduct = binding.edtNamaProduct.text.toString().trim()
        var jual = binding.edtHarga.text.toString().trim()
        var stok = binding.edtStock.text.toString().trim()
        hargaJual = jual.toInt()
        stock = stok.toInt()
        uploadProductViewModel.uploadProduct(codeBarang!!,namaProduct!!,hargaJual,stock)
    }
}