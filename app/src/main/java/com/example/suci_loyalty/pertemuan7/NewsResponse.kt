package com.example.suci_loyalty.pertemuan7

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    // 🛠️ PERBAIKAN: Memetakan 'articles' dari NewsAPI agar otomatis masuk ke variabel 'data' bawaanmu
    @SerializedName("articles")
    val data: List<NewsItem>?
)

data class NewsItem(
    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    // 🛠️ PERBAIKAN: Menggunakan 'urlToImage' yang sesuai dengan format gambar dari NewsAPI
    @SerializedName("urlToImage")
    val image: String?,

    // 🛠️ PERBAIKAN: Menggunakan 'url' yang sesuai dengan format link berita dari NewsAPI
    @SerializedName("url")
    val link: String?
)