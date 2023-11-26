package se.harrison.bitbuddy.data.model

data class Coin(
  val symbol: String,
  val logoUrl: String,
  val priceChange: String,
  val priceChangeRaw: Double,
  val openPrice: String,
  val lastPrice: String,
  val volume: String
)