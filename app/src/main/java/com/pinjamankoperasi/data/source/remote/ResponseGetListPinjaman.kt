package com.pinjamankoperasi.data.source.remote

import com.google.gson.annotations.SerializedName

data class ResponseGetListPinjaman(

	@field:SerializedName("pinjaman")
	val pinjaman: List<PinjamanItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class PinjamanItem(

	@field:SerializedName("jumlah_pinjam")
	val jumlahPinjam: Int? = null,

	@field:SerializedName("id_product")
	val idProduct: Int? = null,

	@field:SerializedName("harga")
	val harga: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("nama_karyawan")
	val namaKaryawan: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("nik_karyawan")
	val nikKaryawan: Int? = null
)
