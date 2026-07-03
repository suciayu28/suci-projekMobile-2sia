package com.example.suci_loyalty.pertemuan10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suci_loyalty.data.entity.JenisSuratEntity
import com.example.suci_loyalty.databinding.ItemJenisSuratBinding

class JenisSuratAdapter(
    private val listJenisSurat: List<JenisSuratEntity>,
    private val onSelectClick: (JenisSuratEntity) -> Unit,
    private val onEditClick: (JenisSuratEntity) -> Unit,
    private val onDeleteClick: (JenisSuratEntity) -> Unit
) : RecyclerView.Adapter<JenisSuratAdapter.ViewHolder>() {

    // Menggunakan Binding untuk menghubungkan ke layout item_jenis_surat.xml
    class ViewHolder(val binding: ItemJenisSuratBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJenisSuratBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listJenisSurat[position]

        // Memasukkan data ke dalam XML yang sudah "mantap" tadi
        holder.binding.tvKodeSurat.text = "KODE: ${item.kode}"
        holder.binding.tvNamaJenis.text = item.nama
        holder.binding.tvDeskripsiSingkat.text = item.deskripsi

        // Logika warna ikon otomatis agar tampilan 6 suratnya bervariasi
        val colors = listOf("#3498DB", "#27AE60", "#E67E22", "#9B59B6", "#F1C40F", "#E74C3C")
        holder.binding.imgIconSurat.setColorFilter(
            android.graphics.Color.parseColor(colors[position % colors.size])
        )

        // Setup klik tombol
        holder.binding.btnLihat.setOnClickListener {
            onSelectClick(item)
        }

        holder.binding.btnEdit.setOnClickListener {
            onEditClick(item)
        }

        holder.binding.btnHapus.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = listJenisSurat.size
}