package com.pinjamankoperasi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinjamankoperasi.data.repository.StockRepository
import com.pinjamankoperasi.data.source.remote.ResponseGetProduct
import com.pinjamankoperasi.data.source.remote.ResponseStockKeluarMonth
import com.pinjamankoperasi.data.source.remote.ResponseStockKeluarYear
import com.pinjamankoperasi.data.source.remote.ResponseStockMasukMonth
import com.pinjamankoperasi.data.source.remote.ResponseStockMasukYear
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.launch

class StockViewModel : ViewModel() {

    private val apiConfig = ApiConfig()
    private val stokRepo = StockRepository(apiConfig)

    private val _getStockMonthResult = MutableLiveData<ResponseStockMasukMonth>()
    val getStockMonthResult : LiveData<ResponseStockMasukMonth> = _getStockMonthResult

    private val _getStockYearResult = MutableLiveData<ResponseStockMasukYear>()
    val getStockYearResult : LiveData<ResponseStockMasukYear> = _getStockYearResult

    private val _getOutStockMonthResult = MutableLiveData<ResponseStockKeluarMonth>()
    val getOutStockMonthResult : LiveData<ResponseStockKeluarMonth> = _getOutStockMonthResult

    private val _getOutStockYearResult = MutableLiveData<ResponseStockKeluarYear>()
    val getOutStockYearResult : LiveData<ResponseStockKeluarYear> = _getOutStockYearResult

    fun getStockMonth(bulan: String, tahun: Int) {
        viewModelScope.launch {
            try {
                val response = stokRepo.getStockMonth(bulan, tahun)
                _getStockMonthResult.postValue(response)
            }catch (e : Exception){
                _getStockMonthResult.postValue(ResponseStockMasukMonth(message = e.message))
            }
        }
    }
    fun getStockYear(tahun: Int) {
        viewModelScope.launch {
            try {
                val response = stokRepo.getStockYear(tahun)
                _getStockYearResult.postValue(response)
            }catch (e : Exception){
                _getStockYearResult.postValue(ResponseStockMasukYear(message = e.message))
            }
        }
    }
    fun getOutStockMonth(bulan: String, tahun: Int) {
        viewModelScope.launch {
            try {
                val response = stokRepo.outStockMonth(bulan, tahun)
                _getOutStockMonthResult.postValue(response)
            }catch (e : Exception){
                _getOutStockMonthResult.postValue(ResponseStockKeluarMonth(message = e.message))
            }
        }
    }
    fun getOutStockYear(tahun: Int) {
        viewModelScope.launch {
            try {
                val response = stokRepo.outStockYear(tahun)
                _getOutStockYearResult.postValue(response)
            }catch (e : Exception){
                _getOutStockYearResult.postValue(ResponseStockKeluarYear(message = e.message))
            }
        }
    }
}