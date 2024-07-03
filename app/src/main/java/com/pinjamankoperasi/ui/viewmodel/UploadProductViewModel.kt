package com.pinjamankoperasi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinjamankoperasi.data.repository.UploadProductRepository
import com.pinjamankoperasi.data.source.remote.ResponseUploadProduct
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.launch

class UploadProductViewModel : ViewModel() {

    private val apiConfig = ApiConfig()
    private val uploadProductRepo = UploadProductRepository(apiConfig)

    private val _uploadProductResult = MutableLiveData<ResponseUploadProduct>()
    val uploadProductResult : LiveData<ResponseUploadProduct> = _uploadProductResult

    fun uploadProduct(codeBarang: String, namaProduct: String,hargaJual: Int,
                      stockMasuk: Int) {
        viewModelScope.launch {
            try {
                val response = uploadProductRepo.uploadProduct(codeBarang, namaProduct, hargaJual, stockMasuk)
                _uploadProductResult.postValue(response)
            }catch (e: Exception){
                _uploadProductResult.postValue(ResponseUploadProduct(message = e.message))
            }
        }
    }

}