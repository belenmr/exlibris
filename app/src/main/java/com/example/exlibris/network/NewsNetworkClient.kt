package com.example.exlibris.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NewsNetworkClient {
    private const val BASE_URL = "https://demo5040351.mockable.io"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val newsApi = retrofit.create(NewsApi::class.java)
}