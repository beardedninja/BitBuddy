package se.harrison.bitbuddy.utils

import java.text.DecimalFormat

fun Double.toMoney(): String {
  val formatter = DecimalFormat("#,##0.##")
  return formatter.format(this)
}

enum class LoadState {
  IDLE,
  LOADING,
  REFRESHING,
  LOADED,
  ERROR
}