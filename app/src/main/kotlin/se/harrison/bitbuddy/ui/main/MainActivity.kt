package se.harrison.bitbuddy.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import se.harrison.bitbuddy.ui.theme.BitBuddyTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      BitBuddyTheme {
        MainScreen()
      }
    }
  }
}

