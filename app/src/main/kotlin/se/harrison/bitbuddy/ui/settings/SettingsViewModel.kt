package se.harrison.bitbuddy.ui.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import se.harrison.bitbuddy.data.model.Currency
import se.harrison.bitbuddy.data.repository.RateRepository

class SettingsViewModel(private val rateRepository: RateRepository): ViewModel() {
  private val _currency = MutableStateFlow(rateRepository.defaultCurrency())
  val currency: StateFlow<Currency> get() = _currency
  
  fun updateCurrency(currency: Currency) {
    _currency.value = currency
  }
  
  fun supportedCurrencies(): List<Currency> {
    return rateRepository.supportedCurrencies()
  }
}