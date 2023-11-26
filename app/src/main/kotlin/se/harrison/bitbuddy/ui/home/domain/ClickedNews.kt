package se.harrison.bitbuddy.ui.home.domain

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import se.harrison.bitbuddy.data.model.Article

class ClickedNews {
  operator fun invoke(activity: ComponentActivity, article: Article) {
    val intent = Intent(
      Intent.ACTION_VIEW, Uri.parse(
      article.link))
    activity.startActivity(intent)
  }
}