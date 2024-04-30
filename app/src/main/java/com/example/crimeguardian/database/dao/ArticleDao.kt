package com.example.crimeguardian.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.crimeguardian.database.TABLE_NAME_MOVIE
import com.example.crimeguardian.database.models.ArticleDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM $TABLE_NAME_MOVIE")
    suspend fun getAll(): List<ArticleDBO>

    @Query("SELECT * FROM $TABLE_NAME_MOVIE")
    fun observeAll(): Flow<List<ArticleDBO>>

    @Insert
    suspend fun insert(articles: List<ArticleDBO>)

    @Delete
    suspend fun remove(articles: List<ArticleDBO>)

    @Query("DELETE FROM $TABLE_NAME_MOVIE")
    suspend fun clean()

}