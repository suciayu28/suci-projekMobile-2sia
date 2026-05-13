package com.example.suci_loyalty.pertemuan10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suci_loyalty.databinding.ItemRiwayatSuratBinding

// Class ini adalah Adapter, bukan Activity
class RiwayatSuratAdapter(private val listRiwayat: List<RiwayatSuratModel>) :
    RecyclerView.Adapter<RiwayatSuratAdapter.ViewHolder>() {

    // Menghubungkan ke layout item_riwayat_surat.xml melalui View Binding
    class ViewHolder(val binding: ItemRiwayatSuratBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRiwayatSuratBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listRiwayat[position]

        // Memasukkan data dari Model ke TextView di XML
        holder.binding.tvNomorSurat.text = item.nomorPermohonan
        holder.binding.tvStatus.text = item.status
        holder.binding.tvTanggal.text = item.waktu

        // Contoh logika warna berdasarkan status (Warna Bina Desa: #B53F6A)
        if (item.status == "Ditolak") {
            holder.binding.tvStatus.setTextColor(android.graphics.Color.RED)
        } else {
            holder.binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#B53F6A"))
        }
    }

    override fun getItemCount(): Int = listRiwayat.size
}