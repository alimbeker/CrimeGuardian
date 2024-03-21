package com.example.crimeguardian.module

import com.example.crimeguardian.api.TengriNewsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TengriNewsApiData {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // API response interceptor
    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    // Client
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    fun getApi(): TengriNewsApi {
        return getRetrofit()
            .create(TengriNewsApi::class.java)
    }

    companion object {
        private const val baseUrl = "https://tengrinews.kz/"
    }
}

