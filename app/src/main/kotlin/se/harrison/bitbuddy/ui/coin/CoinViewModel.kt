package se.harrison.bitbuddy.ui.coin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import se.harrison.bitbuddy.data.ApiResponse
import se.harrison.bitbuddy.data.model.Coin
import se.harrison.bitbuddy.data.model.Currency
import se.harrison.bitbuddy.data.repository.CoinRepository
import se.harrison.bitbuddy.data.repository.RateRepository
import se.harrison.bitbuddy.utils.LoadState

class CoinViewModel(private val coinRepository: CoinRepository, private val rateRepository: RateRepository) : ViewModel() {
  private val _coins = MutableStateFlow<List<Coin>>(listOf())
  val coins: StateFlow<List<Coin>> get() = _coins
  
  private val _loadState = MutableStateFlow(LoadState.IDLE)
  val loadState: StateFlow<LoadState> get() = _loadState
  
  private var previousCurrency: Currency? = null
  
  fun refreshCoins(currency: Currency) {
    _loadState.value = LoadState.REFRESHING
    fetchCoins(currency)
  }
  
  fun loadCoins(currency: Currency) {
    if (previousCurrency == null || previousCurrency != currency) {
      previousCurrency = currency
      _loadState.value = LoadState.LOADING
      fetchCoins(currency)
    } else {
      _loadState.value = LoadState.LOADED
      return
    }
  }
  
  private fun fetchCoins(currency: Currency) {
    viewModelScope.launch(Dispatchers.IO) {
      currency.rate = rateRepository.getRateForCurrency(currency)
      when (val response = coinRepository.getCoins(currency)) {
        is ApiResponse.Success -> {
          withContext(Dispatchers.Main) {
            _coins.value = response.data
            _loadState.value = LoadState.LOADED
          }
        }
        is ApiResponse.Error -> {
          withContext(Dispatchers.Main) {
            _loadState.value = LoadState.ERROR
          }
        }
      }
    }
  }
}