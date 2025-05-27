package com.example.lbwatch.dataLayer.api

import com.example.lbwatch.dataLayer.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface ClientAPI {
    @GET("/")
    suspend fun fetchResponse(@Query("apikey") apiKey: String, @Query("s") query: String): SearchResponse

    companion object {
        fun create(): ClientAPI {
            return Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(ClientAPI::class.java)
        }
    }
}

