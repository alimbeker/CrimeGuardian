package com.example.crimeguardian.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.crimeguardian.database.dao.ArticleDao
import com.example.crimeguardian.database.models.ArticleDBO


@Database(
    entities = [
        ArticleDBO::class
    ],
    version = 1
)
@TypeConverters(
    Converters::class
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}