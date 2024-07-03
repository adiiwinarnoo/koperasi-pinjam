package com.pinjamankoperasi.data.repository

import com.pinjamankoperasi.data.source.remote.ResponseGetProduct
import com.pinjamankoperasi.network.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProductRepository (private val apiConfig: ApiConfig) {

    suspend fun getAllProduct() : ResponseGetProduct {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiConfig.server.getAllProduct().execute()
                if (response.isSuccessful){
                    getAllProductSuccess(response)
                }else{
                    ResponseGetProduct(message = response.message() ?: "Unkwon Error" )
                }
            }catch ( e : Exception){
                ResponseGetProduct(message = e.message ?: "Unkwon Error")
            }
        }
    }

    fun getAllProductSuccess(response : Response<ResponseGetProduct>) : ResponseGetProduct {
        return when (response.code()) {
            200 -> response.body() ?: ResponseGetProduct()
            else -> ResponseGetProduct()
        }
    }

}