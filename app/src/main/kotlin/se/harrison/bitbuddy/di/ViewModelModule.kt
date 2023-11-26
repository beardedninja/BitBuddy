package se.harrison.bitbuddy.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import se.harrison.bitbuddy.ui.home.HomeViewModel
import se.harrison.bitbuddy.ui.coin.CoinViewModel
import se.harrison.bitbuddy.ui.settings.SettingsViewModel

val viewModelModule = module {
  viewModelOf(::HomeViewModel)
  viewModelOf(::CoinViewModel)
  viewModelOf(::SettingsViewModel)
}