package se.harrison.bitbuddy.data.model

data class RateResponse(
  val success: Boolean,
  val timestamp: Long,
  val base: String,
  val date: String,
  val rates: Map<String, Double>
)