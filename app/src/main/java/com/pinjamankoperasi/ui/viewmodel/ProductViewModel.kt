package com.pinjamankoperasi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pinjamankoperasi.data.repository.ProductRepository
import com.pinjamankoperasi.data.source.remote.ResponseGetProduct
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val apiConfig = ApiConfig()
    private val productRepo = ProductRepository(apiConfig)

    private val _getProductResult = MutableLiveData<ResponseGetProduct>()
    val getProductResult : LiveData<ResponseGetProduct> = _getProductResult

    fun getAllProductViewModel() {
        viewModelScope.launch {
            try {
                val response = productRepo.getAllProduct()
                _getProductResult.postValue(response)
            }catch (e : Exception){
                _getProductResult.postValue(ResponseGetProduct(message = e.message))
            }
        }
    }

}