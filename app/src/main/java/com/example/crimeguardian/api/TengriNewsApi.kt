package com.example.crimeguardian.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TengriNewsApi {
    @GET("services/analytics/api/get/widget/data")
    suspend fun getPageData(): List<Page?>
}