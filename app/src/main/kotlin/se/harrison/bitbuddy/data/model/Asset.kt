package se.harrison.bitbuddy.data.model

import com.google.gson.annotations.SerializedName
import se.harrison.bitbuddy.data.Config
import se.harrison.bitbuddy.utils.toMoney

data class Asset(
  @SerializedName("symbol") val symbol: String,
  @SerializedName("priceChange") val priceChange: Double,
  @SerializedName("priceChangePercent") val priceChangePercent: Double,
  @SerializedName("weightedAvgPrice") val weightedAvgPrice: Double,
  @SerializedName("prevClosePrice") val prevClosePrice: Double,
  @SerializedName("lastPrice") val lastPrice: Double,
  @SerializedName("lastQty") val lastQty: Double,
  @SerializedName("bidPrice") val bidPrice: Double,
  @SerializedName("bidQty") val bidQty: Double,
  @SerializedName("askPrice") val askPrice: Double,
  @SerializedName("askQty") val askQty: Double,
  @SerializedName("openPrice") val openPrice: Double,
  @SerializedName("highPrice") val highPrice: Double,
  @SerializedName("lowPrice") val lowPrice: Double,
  @SerializedName("volume") val volume: Double,
  @SerializedName("quoteVolume") val quoteVolume: Double,
  @SerializedName("openTime") val openTime: Long,
  @SerializedName("closeTime") val closeTime: Long,
  @SerializedName("firstId") val firstId: Long,
  @SerializedName("lastId") val lastId: Long,
  @SerializedName("count") val count: Long
) {
  fun getFormattedSymbol(): String {
    return symbol.replace("EUR", "")
  }
  
  fun getSymbolLogoUrl(): String {
    return "${Config.Api.COIN_LOGO_URL}${getFormattedSymbol().lowercase()}.png"
  }

  fun getFormattedPriceChange(currency: Currency): String {
    val sign = if (priceChange > 0) "+" else "-"
    return if (currency.prefix) {
      "$sign${currency.code}${convert(priceChange, currency).toMoney()} ($priceChangePercent%)"
    } else {
      "$sign${convert(priceChange, currency).toMoney()} ${currency.code} ($priceChangePercent%)"
    }
  }
  
  fun getFormattedVolume(currency: Currency): String {
    return if (currency.prefix) {
      "${currency.code}${convert(volume, currency).toMoney()}"
    } else {
      "${convert(volume, currency).toMoney()} ${currency.code}"
    }
  }
  
  fun getFormattedPriceLastPrice(currency: Currency): String {
    return if (currency.prefix) {
      "${currency.code}${convert(lastPrice, currency).toMoney()}"
    } else {
      "${convert(lastPrice, currency).toMoney()} ${currency.code}"
    }
  }
  
  fun getFormattedPriceOpenPrice(currency: Currency): String {
    return if (currency.prefix) {
      "${currency.code}${convert(openPrice, currency).toMoney()}"
    } else {
      "${convert(openPrice, currency).toMoney()} ${currency.code}"
    }
  }
  
  private fun convert(amount: Double, currency: Currency): Double {
    return if (amount == 0.0) {
      0.0
    } else {
      if (amount < 0) {
        amount * currency.rate * -1
      } else {
        amount * currency.rate
      }
    }
  }
}