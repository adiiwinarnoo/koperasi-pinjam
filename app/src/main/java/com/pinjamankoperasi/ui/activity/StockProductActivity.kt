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
import com.pinjamankoperasi.databinding.ActivityStockProductBinding
import com.pinjamankoperasi.ui.viewmodel.StockViewModel
import com.pinjamankoperasi.utils.CustomPicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class StockProductActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStockProductBinding

    var bulan : String? = null
    var tahun = 0
    val myCalendar = Calendar.getInstance()
    var currentDate : String? = null
    private lateinit var viewModel : StockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(StockViewModel::class.java)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.US)
        currentDate = dateFormat.format(Date())
        var tahunSub = currentDate?.substring(0,4)
        tahun = tahunSub!!.toInt()
        bulan = currentDate?.substring(5,7)

        viewModel.getStockMonth(bulan!!,tahun!!)
        viewModel.getStockYear(tahun)
        viewModel.getOutStockMonth(bulan!!, tahun)
        viewModel.getOutStockYear(tahun)

        binding.btnMonth.setOnClickListener {
            getMonth()
        }

        viewModel.getStockMonthResult.observe(this){
            binding.tvStockMasukBulanTahun.setText("Jumlah Stock Masuk $bulan-$tahun = ${it.stockMasuk}")
        }
        viewModel.getStockYearResult.observe(this){
            binding.tvStockMasukTahun.setText("Jumlah Stock Masuk Tahun $tahun = ${it.stockMasuk}")
        }
        viewModel.getOutStockMonthResult.observe(this){
            binding.tvStockOutBulanTahun.setText("Jumlah Stock Keluar $bulan-$tahun = ${it.stockKeluar}")
        }
        viewModel.getOutStockYearResult.observe(this){
            binding.tvStockOutTahun.setText("Jumlah Stock Keluar Tahun $tahun = ${it.stockKeluar}")
        }
    }



    fun getMonth(){
        val today = Calendar.getInstance()
        val dialog = CustomPicker(
            this,
            object : CustomPicker.OnDateSetListener {
                override fun onDateSet(year: Int, month: Int) {
                    if (month < 10) bulan = "0$month"
                    else bulan = month.toString()
                    tahun = year

                    viewModel.getStockMonth(bulan!!,year)
                    viewModel.getStockYear(year)
                    viewModel.getOutStockMonth(bulan!!, year)
                    viewModel.getOutStockYear(year)

                    binding.tvStockMasukBulanTahun.setText("Jumlah Stock Masuk Bulan : $bulan dan Tahun $tahun = ")
                    binding.tvStockMasukTahun.setText("Jumlah Stock Masuk Tahun $tahun = ")
                    binding.tvStockOutBulanTahun.setText("Jumlah Stock Keluar Bulan : $bulan dan Tahun $tahun ")
                    binding.tvStockOutTahun.setText("Jumlah Stock Kelaur Tahun $tahun = ")

                }
            },
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH)
        )

        dialog.show()
    }
}