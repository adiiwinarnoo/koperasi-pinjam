package com.pinjamankoperasi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinjamankoperasi.data.repository.GetListPinjamanRepository
import com.pinjamankoperasi.data.source.remote.ResponseGetListPinjaman
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.launch

class getListPinjamanViewModel : ViewModel() {
    private val apiConfig = ApiConfig()
    private val listRepository = GetListPinjamanRepository(apiConfig)

    private val _listResult = MutableLiveData<ResponseGetListPinjaman>()
    val listResult : LiveData<ResponseGetListPinjaman> = _listResult

    fun getList(tanggal: String) {
        viewModelScope.launch {
            try {
                val response = listRepository.getListPinjaman(tanggal)
                _listResult.postValue(response)
                Log.d("ADAPTER-LIST", "getList-vm: ${response.pinjaman}")
            }catch (e: Exception){
                _listResult.postValue(ResponseGetListPinjaman(message = e.message))
            }
        }
    }
}