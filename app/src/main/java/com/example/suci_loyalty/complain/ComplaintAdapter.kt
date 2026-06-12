package com.example.suci_loyalty.complain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suci_loyalty.data.entity.ComplaintEntity
import com.example.suci_loyalty.databinding.ItemComplaintBinding

class ComplaintAdapter(
    private val list: List<ComplaintEntity>,
    private val fragment: ComplaintFragment
) : RecyclerView.Adapter<ComplaintAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemComplaintBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemComplaintBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val complaint = list[position]
        holder.binding.tvItemTitle.text = complaint.title
        holder.binding.tvItemDescription.text = complaint.description

        // Event hapus aduan saat ikon tempat sampah diklik
        holder.binding.btnDeleteComplaint.setOnClickListener {
            fragment.deleteComplaint(complaint)
        }
    }

    override fun getItemCount(): Int = list.size
}