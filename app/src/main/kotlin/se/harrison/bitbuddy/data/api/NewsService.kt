package se.harrison.bitbuddy.data.api

import retrofit2.Response
import retrofit2.http.GET
import se.harrison.bitbuddy.data.model.RssFeed

interface NewsService {
  @GET("arc/outboundfeeds/rss/")
  suspend fun getRss(): Response<RssFeed>
}