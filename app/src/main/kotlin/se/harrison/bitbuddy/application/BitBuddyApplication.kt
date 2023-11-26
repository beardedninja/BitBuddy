package se.harrison.bitbuddy.application

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber
import se.harrison.bitbuddy.BuildConfig
import se.harrison.bitbuddy.data.api.ExchangeRateService
import se.harrison.bitbuddy.data.repository.RateRepository
import se.harrison.bitbuddy.di.networkModule
import se.harrison.bitbuddy.di.repositoryModule
import se.harrison.bitbuddy.di.useCaseModule
import se.harrison.bitbuddy.di.viewModelModule

class BitBuddyApplication : Application() {
  val rateRepository: RateRepository by inject()
  val applicationScope = CoroutineScope(Dispatchers.IO)
  
  override fun onCreate() {
    super.onCreate()
    
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    
    startKoin {
      androidContext(this@BitBuddyApplication)
      modules(
        listOf(
          viewModelModule,
          repositoryModule,
          networkModule,
          useCaseModule
        )
      )
    }
    
    applicationScope.launch {
      rateRepository.cacheRates()
    }
  }
}