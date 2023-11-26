package se.harrison.bitbuddy.di

import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import se.harrison.bitbuddy.data.api.CoinService
import se.harrison.bitbuddy.data.Config
import se.harrison.bitbuddy.data.api.ExchangeRateService
import se.harrison.bitbuddy.data.api.NewsService

val networkModule = module {
  single { buildService(Config.Api.NEWS_BASE_URL, SimpleXmlConverterFactory.create(), NewsService::class.java) }
  single { buildService(Config.Api.COIN_BASE_URL, GsonConverterFactory.create(), CoinService::class.java) }
  single { buildService(Config.Api.EXCHANGE_RATE_BASE_URL, GsonConverterFactory.create(), ExchangeRateService::class.java) }
}

private fun <T> buildService(baseUrl: String, converterFactory: Converter.Factory, serviceClass: Class<T>): T {
  val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(converterFactory)
    .build()

  return retrofit.create(serviceClass)
}