package com.example.crimeguardian.module

import com.example.crimeguardian.api.Page
import com.example.crimeguardian.api.TengriNewsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val baseUrl = "https://tengrinews.kz/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(): TengriNewsApi {
        return getRetrofit()
            .create(TengriNewsApi::class.java)
    }

    suspend fun getPageData(): List<Page> {
        return apiService.getPageData()
    }
}

