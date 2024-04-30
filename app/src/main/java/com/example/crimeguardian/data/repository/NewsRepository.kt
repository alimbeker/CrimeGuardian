package com.example.crimeguardian.data.repository

import android.util.Log
import com.example.crimeguardian.core.NetworkChecker
import com.example.crimeguardian.core.functional.State
import com.example.crimeguardian.data.api.NewsApi
import com.example.crimeguardian.data.mapper.toArticle
import com.example.crimeguardian.data.mapper.toArticleDbo
import com.example.crimeguardian.database.dao.ArticleDao
import com.example.crimeguardian.presentation.model.model.news.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NewsRepository {

    val observeNewsStateFlow: Flow<State<List<Article>>>

    suspend fun getAllData()
}

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao,
    private val networkChecker: NetworkChecker
) : NewsRepository {

    private val _newsDataFlow = MutableStateFlow<State<List<Article>>>(State.Initial)
    override val observeNewsStateFlow: Flow<State<List<Article>>>
        get() = _newsDataFlow

    override suspend fun getAllData() =
        withContext(Dispatchers.IO) {
            try {
                val cachedMovies = articleDao.getAll()
                if (cachedMovies.isEmpty()) {
                    _newsDataFlow.emit(State.Loading)
                } else {
                    Log.d("PopMovie Repo", "Cache Film")
                    _newsDataFlow.emit(State.Data(cachedMovies.map { it.toArticle() }))
                }
                if (networkChecker.isConnected) {
                    Log.d("PopMovie Repo", "Internet is connected")
                    val articles = newsApi.getAllData(search = "assault").articles
                    _newsDataFlow.emit(State.Data(articles.map { it.toArticle()}))
                    articleDao.clearAndInsert(articles.map { it.toArticleDbo() })
                } else if (cachedMovies.isEmpty()) {
                    _newsDataFlow.emit(State.Failure(Exception("No Internet and no cache")))
                }
            } catch (throwable: Throwable) {
                _newsDataFlow.emit(State.Failure(throwable))
            }
        }
}




