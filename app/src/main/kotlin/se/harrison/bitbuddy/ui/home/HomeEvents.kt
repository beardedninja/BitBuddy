package se.harrison.bitbuddy.ui.home

import androidx.activity.ComponentActivity
import se.harrison.bitbuddy.data.model.Article
import se.harrison.bitbuddy.data.model.Item

sealed class HomeEvents {
  data class ClickedNews(val activity: ComponentActivity, val article: Article) : HomeEvents()
}
