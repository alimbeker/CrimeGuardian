package com.example.crimeguardian.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crimeguardian.core.functional.Resource
import com.example.crimeguardian.core.functional.onFailure
import com.example.crimeguardian.core.functional.onSuccess
import com.example.crimeguardian.data.repository.NewsRepository
import com.example.crimeguardian.module.NewsApiData
import com.example.crimeguardian.presentation.model.model.NewsResponse
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository: NewsRepository = NewsRepository(NewsApiData.getApi())

    private val _newsResponseLiveData = MutableLiveData<Resource<NewsResponse?>>()
    val newsResponseLiveData: LiveData<Resource<NewsResponse?>> = _newsResponseLiveData

    fun getAllData() {
        _newsResponseLiveData.value = Resource.Loading
        viewModelScope.launch {
            repository.getAllData()
                .onFailure { throwable ->
                    _newsResponseLiveData.value = Resource.Error(throwable)
                }
                .onSuccess { newsResponse ->
                    _newsResponseLiveData.value = Resource.Success(newsResponse)
                }
        }
    }

}


