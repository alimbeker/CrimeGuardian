package com.example.crimeguardian.module

import com.example.crimeguardian.api.Page
import com.example.crimeguardian.api.TengriNewsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://tengrinews.kz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(TengriNewsApi::class.java)

    suspend fun getPageData(): List<Page> {
        return apiService.getPageData()
    }
}