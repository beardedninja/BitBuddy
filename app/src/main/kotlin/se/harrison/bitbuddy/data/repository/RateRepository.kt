package se.harrison.bitbuddy.data.repository

import se.harrison.bitbuddy.data.api.ExchangeRateService
import se.harrison.bitbuddy.data.model.Currency

class RateRepository(private val rateService: ExchangeRateService) {
  private val rates: MutableMap<String, Double> = mutableMapOf()
  private val defaultCurrency =
    Currency("European Union", "Euro", "€", "EUR")
  
  private val currencies = listOf(
    defaultCurrency,
    Currency("United States", "United States Dollar", "$", "USD"),
    Currency("United Kingdom", "British Pound", "£", "GBP"),
    Currency("Japan", "Japanese Yen", "¥", "JPY"),
    Currency("Switzerland", "Swiss Franc", "CHF", "CHF"),
    Currency("Australia", "Australian Dollar", "AU$", "AUD"),
    Currency("Canada", "Canadian Dollar", "CA$", "CAD"),
    Currency("China", "Chinese Yuan", "¥", "CNY"),
    Currency("India", "Indian Rupee", "₹", "INR"),
    Currency("Russia", "Russian Ruble", "₽", "RUB"),
    Currency("South Korea", "South Korean Won", "₩", "KRW"),
    Currency("Brazil", "Brazilian Real", "R$", "BRL"),
    Currency("Mexico", "Mexican Peso", "Mex$", "MXN"),
    Currency("South Africa", "South African Rand", "R", "ZAR"),
    Currency("Israel", "Israeli Shekel", "₪", "ILS"),
    Currency("Turkey", "Turkish Lira", "₺", "TRY"),
    Currency("Sweden", "Swedish Krona", "kr", "SEK", false),
    Currency("Norway", "Norwegian Krona", "kr", "NOK", false)
  )
  
  fun defaultCurrency(): Currency {
    return defaultCurrency
  }
  
  fun supportedCurrencies(): List<Currency> {
    return currencies
  }
  
  suspend fun cacheRates() {
    val response = rateService.getRates()
    
    if (response.isSuccessful) {
      response.body()?.rates?.let { newRates ->
        rates.clear()
        rates.putAll(newRates)
      }
    }
  }
  
  fun getRateForCurrency(currency: Currency): Double {
    return rates[currency.symbol] ?: 0.0
  }
}