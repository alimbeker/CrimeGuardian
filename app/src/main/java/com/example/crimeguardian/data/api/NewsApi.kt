package com.example.crimeguardian.data.api

import com.example.crimeguardian.BuildConfig
import com.example.crimeguardian.data.model.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    suspend fun getAllData(
        @Query("apiKey") key: String = BuildConfig.NEWS_API_KEY,
        @Query("q") search: String,
    ): NewsResponseDto
}

