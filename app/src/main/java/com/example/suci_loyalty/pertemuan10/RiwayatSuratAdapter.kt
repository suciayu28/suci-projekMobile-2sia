package com.example.suci_loyalty.pertemuan10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suci_loyalty.data.entity.PermohonanSuratEntity
import com.example.suci_loyalty.databinding.ItemRiwayatSuratBinding

// Class ini adalah Adapter, bukan Activity
class RiwayatSuratAdapter(private val listRiwayat: List<PermohonanSuratEntity>) :
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
        holder.binding.tvNomorSurat.text = "${item.nomorPermohonan} (${item.jenisSurat})"
        holder.binding.tvStatus.text = item.status
        holder.binding.tvTanggal.text = item.waktu

        // Contoh logika warna berdasarkan status
        when (item.status) {
            "Ditolak" -> {
                holder.binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#C53030")) // Dark red
                holder.binding.tvStatus.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FED7D7")) // Light red
            }
            "Selesai" -> {
                holder.binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#22543D")) // Dark green
                holder.binding.tvStatus.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#C6F6D5")) // Light green
            }
            else -> {
                holder.binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#2B6CB0")) // Dark blue
                holder.binding.tvStatus.backgroundTintList = android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#EBF8FF")) // Light blue
            }
        }
    }

    override fun getItemCount(): Int = listRiwayat.size
}