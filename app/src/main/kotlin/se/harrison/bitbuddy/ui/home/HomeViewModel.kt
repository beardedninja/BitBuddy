package se.harrison.bitbuddy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.harrison.bitbuddy.data.ApiResponse
import se.harrison.bitbuddy.data.model.Article
import se.harrison.bitbuddy.data.repository.NewsRepository
import se.harrison.bitbuddy.ui.home.domain.HomeUseCases
import se.harrison.bitbuddy.utils.LoadState

class HomeViewModel(private val newsRepository: NewsRepository, private val homeUseCases: HomeUseCases): ViewModel() {
  private val _articles = MutableStateFlow<List<Article>>(listOf())
  val articles: StateFlow<List<Article>> get() = _articles
  
  private val _loadState = MutableStateFlow(LoadState.IDLE)
  val loadState: StateFlow<LoadState> get() = _loadState
  
  fun onEvent(event: HomeEvents) {
    when(event) {
      is HomeEvents.ClickedNews -> {
        homeUseCases.clickedNews(event.activity, event.article)
      }
    }
  }
  
  fun loadArticles() {
    _loadState.value = LoadState.LOADING
    fetchArticles()
  }
  
  fun refreshArticles() {
    _loadState.value = LoadState.REFRESHING
    fetchArticles()
  }
  
  private fun fetchArticles() {
    viewModelScope.launch(Dispatchers.IO) {
  
      when(val response = newsRepository.getNews()) {
        is ApiResponse.Success -> {
          withContext(Dispatchers.Main) {
            _articles.value = response.data
            _loadState.value = LoadState.LOADED
          }
        }
        is ApiResponse.Error -> {
          withContext(Dispatchers.Main) {
            _loadState.value = LoadState.ERROR
          }
        }
      }
    }
  }
}