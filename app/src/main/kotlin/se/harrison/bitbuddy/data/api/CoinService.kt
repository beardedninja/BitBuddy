package se.harrison.bitbuddy.data.api

import retrofit2.Response
import retrofit2.http.GET
import se.harrison.bitbuddy.data.model.Asset

interface CoinService {
  @GET("ticker/24hr")
  suspend fun getAssets(): Response<List<Asset>>
}

