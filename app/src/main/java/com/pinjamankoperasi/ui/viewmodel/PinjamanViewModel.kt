package com.pinjamankoperasi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinjamankoperasi.data.repository.PinjamanRepository
import com.pinjamankoperasi.data.source.remote.ResponsePinjaman
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.launch

class PinjamanViewModel : ViewModel() {
    private val apiConfig = ApiConfig()
    private val pinjamanRepository = PinjamanRepository(apiConfig)

    private val _pinjamanResult = MutableLiveData<ResponsePinjaman>()
    val pinjamanResult : LiveData<ResponsePinjaman> = _pinjamanResult

    fun uploadPinjaman(idProduct : Int,nikKaryawan : Int, namaKaryawan: String,
                       jumlahPinjam: Int,harga: Int){
        viewModelScope.launch {
            try {
                val response = pinjamanRepository.uploadPinjaman(idProduct, nikKaryawan, namaKaryawan, jumlahPinjam, harga)
                _pinjamanResult.postValue(response)
            }catch (e: Exception){
                _pinjamanResult.postValue(ResponsePinjaman(message = e.message))
            }
        }
    }
}