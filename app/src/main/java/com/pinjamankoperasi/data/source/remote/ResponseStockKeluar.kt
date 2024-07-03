package com.pinjamankoperasi.data.source.remote

import com.google.gson.annotations.SerializedName

data class ResponseStockKeluarMonth(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("stock_keluar")
	val stockKeluar: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ResponseStockMasukMonth(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("stock_masuk")
	val stockMasuk: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ResponseStockKeluarYear(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("stock_keluar")
	val stockKeluar: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ResponseStockMasukYear(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("stock_masuk")
	val stockMasuk: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

