package com.pinjamankoperasi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.pinjamankoperasi.R
import com.pinjamankoperasi.databinding.ActivityPinjamanBinding
import com.pinjamankoperasi.ui.viewmodel.PinjamanViewModel
import com.pinjamankoperasi.ui.viewmodel.ProductViewModel

class PinjamanActivity : AppCompatActivity() {

    lateinit var binding : ActivityPinjamanBinding
    lateinit var productViewModel : ProductViewModel
    lateinit var pinjamanViewModel : PinjamanViewModel
    var productId = 0
    var nomorInduk = 0
    var namaKaryawan : String? = null
    var jumlahPinjam = 0
    var hargaProduct = 0
    var totalHarga = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinjamanBinding.inflate(layoutInflater)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        pinjamanViewModel = ViewModelProvider(this).get(PinjamanViewModel::class.java)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        productViewModel.getAllProductViewModel()

        productViewModel.getProductResult.observe(this) {
            var namaProduct = it.product?.mapNotNull { it?.namaProduct} ?: emptyList()
            var idProduct = it.product?.mapNotNull { it?.id} ?: emptyList()
            var harga = it.product?.mapNotNull { it?.hargaJual } ?: emptyList()

            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, namaProduct)
            binding.spinnerProduct.adapter = arrayAdapter

            binding.spinnerProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedItem = parent?.getItemAtPosition(position) as String
                    productId = idProduct[position]
                    hargaProduct = harga[position]
                    binding.spinnerProduct.setSelection(arrayAdapter.getPosition(selectedItem))
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        pinjamanViewModel.pinjamanResult.observe(this){
            if (it.message.equals("Success")){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                onBackPressed()
            }else{
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.edtJumlahPinjam.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var s3 = s.toString()
                if (s3.isNotEmpty()) {
                    val jumlahPinjam = s3.toInt()
                    totalHarga = hargaProduct * jumlahPinjam
                    binding.edtHarga.setText(totalHarga.toString())
                } else {
                    binding.edtHarga.setText("0")
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnSimpan.setOnClickListener {
            checkedData()
        }

    }
    private fun checkedData(){
        val nomor = binding.edtNik.text.toString().trim()
        namaKaryawan = binding.edtNamaKaryawan.text.toString().trim()
        var pinjam = binding.edtJumlahPinjam.text.toString().trim()
        if (nomor.isEmpty()) binding.edtNik.setError("Nomor Induk Karyawan tidak Boleh Kosong!")
        else if (namaKaryawan.isNullOrEmpty()) binding.edtNik.setError("Nama Karyawan tidak boleh Kosong!")
        else if (pinjam.isNullOrEmpty()) binding.edtNik.setError("Jumlah Pinjam Tidak Boleh Kosong!")
        else{
            nomorInduk = nomor.toInt()
            jumlahPinjam = pinjam.toInt()
            pinjamanViewModel.uploadPinjaman(idProduct = productId, nikKaryawan = nomorInduk,
                namaKaryawan = namaKaryawan!!, jumlahPinjam = jumlahPinjam, harga = totalHarga)
        }
    }
}