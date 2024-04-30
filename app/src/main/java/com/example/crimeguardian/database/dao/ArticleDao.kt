package com.example.crimeguardian.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.crimeguardian.database.TABLE_NAME_MOVIE
import com.example.crimeguardian.database.models.ArticleDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM $TABLE_NAME_MOVIE")
    suspend fun getAll(): List<ArticleDBO>

    @Insert
    suspend fun insert(entities: List<ArticleDBO>)

    @Query("DELETE FROM $TABLE_NAME_MOVIE")
    suspend fun deleteAll()

    @Transaction
    suspend fun clearAndInsert(entities: List<ArticleDBO>) {
        deleteAll()
        insert(entities)
    }


}