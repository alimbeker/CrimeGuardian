package com.example.crimeguardian.data.repository

import com.example.crimeguardian.core.BaseRepository
import com.example.crimeguardian.core.functional.State
import com.example.crimeguardian.data.api.NewsApi
import com.example.crimeguardian.data.mapper.toNewsResponse
import com.example.crimeguardian.presentation.model.model.news.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsRepository(private val api: NewsApi) : BaseRepository() {

    suspend fun getAllData(): State<Throwable, NewsResponse> = apiCall {
        withContext(Dispatchers.IO) {
            api.getAllData(search = "assault").toNewsResponse()
        }
    }
}




