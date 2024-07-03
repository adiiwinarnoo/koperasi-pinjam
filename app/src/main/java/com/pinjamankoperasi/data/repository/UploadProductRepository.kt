package com.pinjamankoperasi.data.repository

import com.google.gson.Gson
import com.pinjamankoperasi.data.source.remote.ResponseGetListPinjaman
import com.pinjamankoperasi.data.source.remote.ResponseUploadProduct
import com.pinjamankoperasi.network.ApiConfig
import com.pinjamankoperasi.utils.Contant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class UploadProductRepository(private val apiConfig: ApiConfig) {
    var textError : String? = null

    suspend fun uploadProduct(codeBarang: String, namaProduct: String,hargaJual: Int,
                              stockMasuk: Int) : ResponseUploadProduct {
        return withContext(Dispatchers.IO){
            try {
                val response = apiConfig.server.uploadProduct(codeBarang, namaProduct, hargaJual, stockMasuk).execute()
                if (response.isSuccessful){
                    uploadProductSuccess(response)
                }else{
                    var errorBody = response.errorBody()?.string()
                    textError = if (errorBody != null){
                        try {
                            val errorResponse = Gson().fromJson(errorBody, ResponseUploadProduct::class.java)
                            errorResponse.message
                        }catch (e:Exception){
                            response.message()
                        }
                    }else{
                        response.message()
                    }
                    ResponseUploadProduct(message = textError)
                }
            }catch (e: Exception){
                ResponseUploadProduct(message = e.message)
            }
        }
    }

    private fun uploadProductSuccess(response: Response<ResponseUploadProduct>): ResponseUploadProduct {
        return when (response.code()) {
            200 -> response.body() ?: ResponseUploadProduct()
            400 -> ResponseUploadProduct(message = Contant.ERROR_TEXT_400)
            401 -> ResponseUploadProduct(message = response.body()?.message)
            404 -> ResponseUploadProduct(message = Contant.ERROR_TEXT_404)
            else -> ResponseUploadProduct()
        }
    }
}