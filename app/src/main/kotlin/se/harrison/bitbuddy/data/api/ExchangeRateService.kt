package se.harrison.bitbuddy.data.api

import retrofit2.Response
import retrofit2.http.GET
import se.harrison.bitbuddy.data.model.RateResponse

interface ExchangeRateService {
  @GET("api/rates")
  suspend fun getRates(): Response<RateResponse>
}