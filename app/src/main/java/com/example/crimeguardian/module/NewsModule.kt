package com.example.crimeguardian.module

import android.app.NotificationManager
import android.content.Context
import androidx.room.Room
import com.example.crimeguardian.core.NetworkChecker
import com.example.crimeguardian.data.api.NewsApi
import com.example.crimeguardian.data.repository.NewsRepository
import com.example.crimeguardian.database.DATABASE_NAME_MOVIES
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
    fun provideNewsDatabase(@ApplicationContext context: Context) : NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            DATABASE_NAME_MOVIES
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(myRoomDatabase: NewsDatabase) : ArticleDao {
        return myRoomDatabase.articleDao()
    }

    @Provides
    @Singleton
    fun provideNetworkChecker(@ApplicationContext context: Context): NetworkChecker {
        return NetworkChecker(context)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        articleDao: ArticleDao,
        newsApi: NewsApi,
        networkChecker: NetworkChecker
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsApi = newsApi,
            articleDao = articleDao,
            networkChecker = networkChecker
        )
    }
}