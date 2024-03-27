package com.example.crimeguardian.data.repository

import android.util.Log
import com.example.crimeguardian.core.BaseRepository
import com.example.crimeguardian.core.functional.State
import com.example.crimeguardian.data.api.NewsApi
import com.example.crimeguardian.data.mapper.toNewsResponse
import com.example.crimeguardian.data.model.NewsApiError
import com.example.crimeguardian.data.model.NewsResponseDto
import com.example.crimeguardian.presentation.model.model.NewsResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody


class NewsRepository(private val api: NewsApi) : BaseRepository() {

    suspend fun getAllData(): State<Throwable, NewsResponse> = apiCall {
        withContext(Dispatchers.IO) {
            api.getAllData(search = "crime").toNewsResponse()
        }
    }
}




