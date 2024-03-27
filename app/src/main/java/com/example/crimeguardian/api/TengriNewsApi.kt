package com.example.crimeguardian.api

import com.example.crimeguardian.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TengriNewsApi {
    @GET("everything")
    suspend fun getAllData(
        @Query("apiKey") key: String = "8c16b8cce1784650bcd2706ff4d568c6",
        @Query("q") search: String,
    ): Response<List<NewsResponse>>
}

