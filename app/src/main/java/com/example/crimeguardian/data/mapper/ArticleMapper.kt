package com.example.crimeguardian.data.mapper

import com.example.crimeguardian.data.model.ArticleDto
import com.example.crimeguardian.database.models.ArticleDBO
import com.example.crimeguardian.database.models.Source
import com.example.crimeguardian.presentation.model.model.news.Article

internal fun ArticleDto.toArticle() = Article(
    title = title ?: "",
    description = description ?: "",
    url = url ?: "",
    urlToImage = urlToImage ?: "",
    sourceName = source?.name ?: "",
)

internal fun ArticleDBO.toArticle(): Article {
    return Article(
        title = title ?: "",
        description = description ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        sourceName = source?.name ?: "",
    )
}

internal fun ArticleDto.toArticleDbo(): ArticleDBO {
    return ArticleDBO(
        source = Source(id = source?.id, name = source?.name) ,
        author = author ?: "",
        title = title ?: "",
        description = description ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        publishedAt = publishedAt ?: "",
        content = content ?: "",
        id = 0
    )
}