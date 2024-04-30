package com.example.crimeguardian.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.crimeguardian.database.TABLE_NAME_MOVIE
import java.util.Date

@Entity(tableName = TABLE_NAME_MOVIE)
data class ArticleDBO(
    @Embedded(prefix = "source")
    val source: Source,
    @ColumnInfo("author")
    val author: String,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("description")
    val description: String,
    @ColumnInfo("url")
    val url: String,
    @ColumnInfo("urlToImage")
    val urlToImage: String?,
    @ColumnInfo("publishedAt")
    val publishedAt: String,
    @ColumnInfo("content")
    val content: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long,
)


data class Source(
    val id: String?,
    val name: String?
)