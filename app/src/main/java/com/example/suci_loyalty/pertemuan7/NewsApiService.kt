package com.example.suci_loyalty.pertemuan7

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    fun getNews(
        @Query("country") country: String = "id",
        @Query("apiKey") apiKey: String = "0c0bf93f2fbe4e599e078f1bc41ef615"
    ): Call<NewsResponse>

    companion object {
        private const val BASE_URL = "https://newsapi.org/"

        fun create(): NewsApiService {
            // 🛠️ PERBAIKAN KRITIS: NewsAPI mewajibkan User-Agent pada platform mobile agar tidak terkena error 403 Forbidden
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .build()
                    chain.proceed(request)
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NewsApiService::class.java)
        }
    }
}