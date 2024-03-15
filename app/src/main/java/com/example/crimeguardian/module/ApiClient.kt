package com.example.crimeguardian.module

import com.example.crimeguardian.api.Page
import com.example.crimeguardian.api.TengriNewsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getApi(): TengriNewsApi {
        return getRetrofit()
            .create(TengriNewsApi::class.java)
    }

    suspend fun getPageData(): List<Page> {
        return getApi().getPageData()
    }

    companion object {
        private const val baseUrl = "https://tengrinews.kz/"
    }
}

