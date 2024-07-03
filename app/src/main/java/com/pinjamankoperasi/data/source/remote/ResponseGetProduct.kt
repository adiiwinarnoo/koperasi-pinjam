package com.pinjamankoperasi.data.source.remote

import com.google.gson.annotations.SerializedName

data class ResponseGetProduct(

	@field:SerializedName("product")
	val product: List<ProductItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ProductItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("stock_masuk")
	val stockMasuk: Int? = null,

	@field:SerializedName("foto_product")
	val fotoProduct: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("nama_product")
	val namaProduct: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("code_barang")
	val codeBarang: String? = null,

	@field:SerializedName("harga_jual")
	val hargaJual: Int? = null
)
