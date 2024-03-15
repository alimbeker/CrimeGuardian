package com.example.crimeguardian.repository

import com.example.crimeguardian.api.Page
import com.example.crimeguardian.api.TengriNewsApiError
import com.example.crimeguardian.module.ApiClient
import com.google.gson.Gson
import okhttp3.ResponseBody


interface PageRepository {
    suspend fun getPageData(): List<Page?>
}

class PageRepositoryImpl(private val api: ApiClient) : PageRepository {

    override suspend fun getPageData(): List<Page?> {
        return api.getPageData()
    }
}

fun ResponseBody?.getErrorMessage(): String? {
    return try {
        Gson().fromJson(this?.charStream(), TengriNewsApiError::class.java)?.error?.message
    } catch (e: Exception) {
        e.message.orEmpty()
    }
}

