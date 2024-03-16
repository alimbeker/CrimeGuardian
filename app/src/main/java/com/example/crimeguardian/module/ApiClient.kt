package com.example.crimeguardian.module

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

    fun getApi(): TengriNewsApi {
        return getRetrofit()
            .create(TengriNewsApi::class.java)
    }

    companion object {
        private const val baseUrl = "https://tengrinews.kz/"
    }
}

