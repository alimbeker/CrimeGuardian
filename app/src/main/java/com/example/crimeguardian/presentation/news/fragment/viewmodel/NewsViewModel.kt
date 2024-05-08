package com.example.crimeguardian.presentation.news.fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.crimeguardian.core.functional.Resource
import com.example.crimeguardian.core.functional.State
import com.example.crimeguardian.data.repository.NewsRepositoryImpl
import com.example.crimeguardian.presentation.model.model.news.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepositoryImpl
): ViewModel() {

    private val _newsLiveData = repository.observeNewsStateFlow
        .map(::mapToUiState)
        .asLiveData()

    val newsLiveData: LiveData<Resource<List<Article>>>
        get() = _newsLiveData



    fun getAllData() {
        viewModelScope.launch {
            repository.getAllData()
        }
    }

    fun getTopHeadlines() {
        viewModelScope.launch {
            repository.getTopHeadlines()
        }
    }

    private fun mapToUiState(newsState: State<List<Article>>) =
        when (newsState) {
            State.Initial -> Resource.Empty
            State.Loading -> Resource.Loading
            is State.Failure -> Resource.Error(newsState.exception)
            is State.Data -> Resource.Success(newsState.data)
        }

}


