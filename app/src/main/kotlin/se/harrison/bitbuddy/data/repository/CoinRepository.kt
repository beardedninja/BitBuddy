package se.harrison.bitbuddy.data.repository

import se.harrison.bitbuddy.data.ApiResponse
import se.harrison.bitbuddy.data.api.CoinService
import se.harrison.bitbuddy.data.model.Coin
import se.harrison.bitbuddy.data.model.Currency


class CoinRepository(private val coinService: CoinService) {
  suspend fun getCoins(currency: Currency): ApiResponse<List<Coin>> {
    val response = coinService.getAssets()
    return if (response.isSuccessful) {
      val assets = response.body()?.filter { it.symbol.endsWith("EUR") && it.count > 0L }
        ?.sortedByDescending { it.lastPrice } ?: emptyList()
      val coins = assets.map {
        Coin(
          it.getFormattedSymbol(),
          it.getSymbolLogoUrl(),
          it.getFormattedPriceChange(currency),
          it.priceChange,
          it.getFormattedPriceOpenPrice(currency),
          it.getFormattedPriceLastPrice(currency),
          it.getFormattedVolume(currency)
        )
      }
      ApiResponse.Success(coins)
    } else {
      ApiResponse.Error(Exception(response.message()))
    }
  }
}