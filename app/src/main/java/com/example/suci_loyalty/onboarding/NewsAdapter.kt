package com.example.suci_loyalty.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suci_loyalty.databinding.ItemNewsBinding
import com.example.suci_loyalty.pertemuan7.NewsItem

class NewsAdapter(private val newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]
        holder.binding.tvTitle.text = item.title
        holder.binding.tvDescription.text = item.description

        // Memuat gambar berita dari internet menggunakan Glide
        Glide.with(holder.itemView.context)
            .load(item.image)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.binding.imgNews)

        // Logika ketika salah satu berita di dalam daftar diklik
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Membuka: ${item.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = newsList.size
}