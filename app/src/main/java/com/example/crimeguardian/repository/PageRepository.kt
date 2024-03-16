package com.example.crimeguardian.repository

import com.example.crimeguardian.api.Page
import com.example.crimeguardian.api.TengriNewsApi
import com.example.crimeguardian.api.TengriNewsApiError
import com.google.gson.Gson
import okhttp3.ResponseBody

interface PageRepository {
    suspend fun getPageData(): List<Page?>?
}

class PageRepositoryImpl(private val api: TengriNewsApi) : PageRepository {

    override suspend fun getPageData(): List<Page?>? {
        val response = api.getPageData()
        if (response.isSuccessful) return response.body()
        else throw Exception(response.errorBody().getErrorMessage())
    }
}

fun ResponseBody?.getErrorMessage(): String? {
    return try {
        Gson().fromJson(this?.charStream(), TengriNewsApiError::class.java)?.error?.message
    } catch (e: Exception) {
        e.message.orEmpty()
    }
}

