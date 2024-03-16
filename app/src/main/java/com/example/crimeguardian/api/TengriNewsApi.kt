package com.example.crimeguardian.api

import retrofit2.Response
import retrofit2.http.GET

interface TengriNewsApi {
    @GET("services/analytics/api/get/widget/data/")
    suspend fun getPageData(): Response<List<Page>>
}

