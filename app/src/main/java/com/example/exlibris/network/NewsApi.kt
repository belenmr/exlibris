package com.example.exlibris.network

import com.example.exlibris.data.BookResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("/books")
    fun getNewBooks(): Call<List<BookResponse>>
}