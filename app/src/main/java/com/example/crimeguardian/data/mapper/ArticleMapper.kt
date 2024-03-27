package com.example.crimeguardian.data.mapper

import com.example.crimeguardian.data.model.ArticleDto
import com.example.crimeguardian.presentation.model.model.Article

internal fun ArticleDto.toArticle() = Article(
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
)