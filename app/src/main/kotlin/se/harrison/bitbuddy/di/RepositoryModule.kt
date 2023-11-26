package se.harrison.bitbuddy.di

import org.koin.dsl.module
import se.harrison.bitbuddy.data.repository.NewsRepository
import se.harrison.bitbuddy.data.repository.CoinRepository
import se.harrison.bitbuddy.data.repository.RateRepository

val repositoryModule = module {
  single { NewsRepository(get()) }
  single { CoinRepository(get()) }
  single { RateRepository(get()) }
}