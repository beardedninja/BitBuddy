package se.harrison.bitbuddy.data.model

data class Currency(
  val countryCode: String,
  val name: String,
  val code: String,
  val symbol: String,
  val prefix: Boolean = true,
  var rate: Double = 0.0
)