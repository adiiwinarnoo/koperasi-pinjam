package com.pinjamankoperasi.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pinjamankoperasi.R
import com.pinjamankoperasi.data.source.remote.PinjamanItem

class ListPinjamanAdapter (private val models :List<PinjamanItem?>?) :
    RecyclerView.Adapter<ListPinjamanAdapter.ViewHolder>() {

        class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
            var nikKaryawan = itemView.findViewById<TextView>(R.id.tv_nik)
            var namaKaryawan = itemView.findViewById<TextView>(R.id.tv_nama)
            var jumlahPinjam = itemView.findViewById<TextView>(R.id.tv_jumlah_pinjam)
            var harga = itemView.findViewById<TextView>(R.id.tv_harga)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_pinjaman_item, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return models!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nikKaryawan.text = models?.get(position)?.nikKaryawan.toString()
        holder.namaKaryawan.text = models?.get(position)?.namaKaryawan.toString()
        holder.jumlahPinjam.text = models?.get(position)?.jumlahPinjam.toString()
        holder.harga.text = models?.get(position)?.harga.toString()
    }
}