package se.harrison.bitbuddy.di

import org.koin.dsl.module
import se.harrison.bitbuddy.ui.home.domain.ClickedNews
import se.harrison.bitbuddy.ui.home.domain.HomeUseCases

val useCaseModule = module {
  single {
    HomeUseCases(
      clickedNews = ClickedNews()
    )
  }
}