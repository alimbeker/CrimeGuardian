package com.example.crimeguardian.repository

import com.example.crimeguardian.data.api.NewsApi
import com.example.crimeguardian.data.model.NewsApiError
import com.example.crimeguardian.data.model.NewsResponse
import com.google.gson.Gson
import okhttp3.ResponseBody

interface PageRepository {
    suspend fun getPageData(): NewsResponse?
}

class PageRepositoryImpl(private val api: NewsApi) : PageRepository {

    override suspend fun getPageData(): NewsResponse? {
        val response = api.getAllData(search = "crime")
        if (response.isSuccessful) return response.body()
        else throw Exception(response.errorBody().getErrorMessage())
    }
}

fun ResponseBody?.getErrorMessage(): String? {
    return try {
        Gson().fromJson(this?.charStream(), NewsApiError::class.java)?.error?.message
    } catch (e: Exception) {
        e.message.orEmpty()
    }
}

