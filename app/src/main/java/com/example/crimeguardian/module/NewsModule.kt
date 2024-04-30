package com.example.crimeguardian.module

import android.app.NotificationManager
import android.content.Context
import com.example.crimeguardian.data.api.NewsApi
import com.example.crimeguardian.data.repository.NewsRepository
import com.example.crimeguardian.database.NewsDatabase
import com.example.crimeguardian.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }

//    @Provides
//    @Singleton
//    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
//        return NetworkChecker(context)
//    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        articleDao: ArticleDao,
        newsApi: NewsApi,
        networkChecker: NetworkChecker
    ): NewsRepository {
        return NewsRepositoryImpl(
            moviesApi = moviesApi,
            movieDao = movieDao,
            networkChecker = networkChecker
        )
    }
}