package com.pinjamankoperasi.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.pinjamankoperasi.R
import com.pinjamankoperasi.data.source.remote.ResponseGetListPinjaman
import com.pinjamankoperasi.data.source.remote.ResponsePinjaman
import com.pinjamankoperasi.databinding.ActivityListPinjamanBinding
import com.pinjamankoperasi.network.ApiConfig
import com.pinjamankoperasi.ui.adapter.ListPinjamanAdapter
import com.pinjamankoperasi.ui.viewmodel.getListPinjamanViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.log

class ListPinjamanActivity : AppCompatActivity() {

    lateinit var binding : ActivityListPinjamanBinding
    lateinit var listViewModel : getListPinjamanViewModel
    lateinit var adapterList : ListPinjamanAdapter
    val myCalendar = Calendar.getInstance()
    var currentDate : String? = null

    private val apiConfig = ApiConfig()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListPinjamanBinding.inflate(layoutInflater)
        listViewModel = ViewModelProvider(this).get(getListPinjamanViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.US)
        currentDate = dateFormat.format(Date())
        binding.tvCurrentDate.setText("Tanggal : ${currentDate}")
        listViewModel.getList(currentDate!!)


        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listViewModel.listResult.observe(this){
            binding.idProgressbar.visibility = View.GONE
            adapterList = ListPinjamanAdapter(it.pinjaman)
            binding.recyclerView.adapter = adapterList
        }


        val date = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month
            myCalendar[Calendar.DAY_OF_MONTH] = day
            val format = "YYYY-MM-dd"
            val dateFormat = SimpleDateFormat(format, Locale.US)
            currentDate = dateFormat.format(myCalendar.time)
            listViewModel.getList(currentDate!!)
            binding.tvCurrentDate.setText("Tanggal : ${dateFormat.format(myCalendar.time)}")
        }

        binding.btnTanggal.setOnClickListener {
            DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

    }
}