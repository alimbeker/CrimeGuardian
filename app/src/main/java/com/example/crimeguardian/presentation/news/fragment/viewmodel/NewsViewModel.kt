package com.example.crimeguardian.presentation.news.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crimeguardian.core.functional.Resource
import com.example.crimeguardian.core.functional.onFailure
import com.example.crimeguardian.core.functional.onSuccess
import com.example.crimeguardian.data.repository.NewsRepository
import com.example.crimeguardian.module.NewsApiData
import com.example.crimeguardian.presentation.model.model.news.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {


    private val _newsResponseLiveData = MutableLiveData<Resource<List<Article>>>()
    val newsResponseLiveData: LiveData<Resource<List<Article>>> = _newsResponseLiveData

    fun getAllData() {
        _newsResponseLiveData.value = Resource.Loading
        viewModelScope.launch {
            repository.getAllData()
                .onFailure { throwable ->
                    _newsResponseLiveData.value = Resource.Error(throwable)
                }
                .onSuccess { newsResponse ->
                    _newsResponseLiveData.value = Resource.Success(newsResponse.articles)
                }
        }
    }

}


