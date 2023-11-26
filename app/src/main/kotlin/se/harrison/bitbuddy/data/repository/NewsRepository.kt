package se.harrison.bitbuddy.data.repository

import se.harrison.bitbuddy.data.ApiResponse
import se.harrison.bitbuddy.data.api.NewsService
import se.harrison.bitbuddy.data.model.Article

class NewsRepository(private val newsService: NewsService) {
  suspend fun getNews(): ApiResponse<List<Article>> {
    val response = newsService.getRss()
    return if (response.isSuccessful) {
      val articles = response.body()?.channel?.items?.map {
        Article(
          it.title,
          it.description,
          it.link,
          it.content.url
        )
      } ?: emptyList()
      ApiResponse.Success(articles)
    } else {
      ApiResponse.Error(Exception(response.message()))
    }
  }
}

