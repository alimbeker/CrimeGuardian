package com.example.crimeguardian.data.api

import com.example.crimeguardian.data.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("everything")
    suspend fun getAllData(
        @Query("apiKey") key: String = "8c16b8cce1784650bcd2706ff4d568c6",
        @Query("q") search: String,
    ): NewsResponseDto
}

