package com.example.exlibris.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object BooksNetworkClient {
    private const val BASE_URL = "http://demo5040351.mockable.io"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val booksApi = retrofit.create(BooksApi::class.java)
}