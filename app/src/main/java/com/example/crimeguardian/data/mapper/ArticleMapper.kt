package com.example.crimeguardian.data.mapper

import com.example.crimeguardian.data.model.ArticleDto
import com.example.crimeguardian.data.model.NewsResponseDto
import com.example.crimeguardian.presentation.model.model.Article
import com.example.crimeguardian.presentation.model.model.NewsResponse

internal fun ArticleDto.toArticle() = Article(
    title = title,
    description = description,
    url = url,
    urlToImage = urlToImage,
)