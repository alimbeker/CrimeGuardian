package com.example.crimeguardian.data.repository

import android.util.Log
import com.example.crimeguardian.core.BaseRepository
import com.example.crimeguardian.core.NetworkChecker
import com.example.crimeguardian.core.functional.State
import com.example.crimeguardian.data.api.NewsApi
import com.example.crimeguardian.data.mapper.toArticle
import com.example.crimeguardian.data.mapper.toNewsResponse
import com.example.crimeguardian.database.dao.ArticleDao
import com.example.crimeguardian.database.models.ArticleDBO
import com.example.crimeguardian.presentation.model.model.news.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NewsRepository {

    val observeNewsStateFlow: Flow<State<List<ArticleDBO>>>

    suspend fun getAllData()
}

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao,
    private val networkChecker: NetworkChecker
) : NewsRepository {

    private val _newsDataFlow = MutableStateFlow<State<List<ArticleDBO>>>(State.Initial)
    override val observeNewsStateFlow: Flow<State<List<ArticleDBO>>>
        get() = _newsDataFlow

    override suspend fun getAllData() =
        withContext(Dispatchers.IO) {
            try {
                val cachedMovies = articleDao.getAll()
                if (cachedMovies.isEmpty()) {
                    _newsDataFlow.emit(State.Loading)
                } else {
                    Log.d("PopMovie Repo", "Cache Film")
                    _newsDataFlow.emit(State.Data(cachedMovies.map { it }))
                }
                if (networkChecker.isConnected) {
                    Log.d("PopMovie Repo", "Internet is connected")
                    val articles = newsApi.getAllData().articles
//                    _newsDataFlow.emit(State.Data(articles.map { it}))
//                    articleDao.clearAndInsert(articles.map { it.toEntity() })
                } else if (cachedMovies.isEmpty()) {
                    _newsDataFlow.emit(State.Failure(Exception("No Internet and no cache")))
                }
            } catch (throwable: Throwable) {
                _newsDataFlow.emit(State.Failure(throwable))
            }
        }
}




