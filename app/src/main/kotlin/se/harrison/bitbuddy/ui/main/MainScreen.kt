package se.harrison.bitbuddy.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel
import se.harrison.bitbuddy.R
import se.harrison.bitbuddy.ui.coin.CoinScreen
import se.harrison.bitbuddy.ui.coin.CoinViewModel
import se.harrison.bitbuddy.ui.home.HomeScreen
import se.harrison.bitbuddy.ui.home.HomeViewModel
import se.harrison.bitbuddy.ui.settings.SettingsScreen
import se.harrison.bitbuddy.ui.settings.SettingsViewModel
import se.harrison.bitbuddy.ui.wallet.WalletScreen

sealed class BottomNavigationScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
  object Home : BottomNavigationScreens("home", R.string.home_screen_route, Icons.Filled.Home)
  object Coins : BottomNavigationScreens("coins", R.string.coins_screen_route, Icons.Filled.CurrencyBitcoin)
  // TODO: Wallet screen
  //object Wallet : BottomNavigationScreens("wallet", R.string.wallet_screen_route, Icons.Filled.Wallet)
  object Settings : BottomNavigationScreens("settings", R.string.settings_screen_route, Icons.Filled.Settings)
  
  companion object {
    fun getScreens() = listOf(Home, Coins, Settings)
  }
}

@Composable
fun MainScreen() {
  val navController = rememberNavController()
  val bottomNavigationItems = BottomNavigationScreens.getScreens()
  
  // This shouldn't be required but due to a bug in Koin, we need to create the viewmodels here and
  // pass them down, otherwise they will be recreated on every navigation.
  val homeViewModel = getViewModel<HomeViewModel>()
  val coinViewModel = getViewModel<CoinViewModel>()
  val settingsViewModel = getViewModel<SettingsViewModel>()
  
  Surface(color = MaterialTheme.colorScheme.background) {
    Scaffold(
      bottomBar = {
        MainNavigationBar(navController, bottomNavigationItems)
      },
    ) { innerPadding ->
      MainScreenNavigation(navController,
        coinViewModel,
        homeViewModel,
        settingsViewModel,
        innerPadding)
    }
  }
}

@Composable
fun MainScreenNavigation(
  navController: NavHostController,
  coinViewModel: CoinViewModel,
  homeViewModel: HomeViewModel,
  settingsViewModel: SettingsViewModel,
  innerPadding: PaddingValues
) {
  NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
    composable(BottomNavigationScreens.Home.route) {
      HomeScreen(homeViewModel)
    }
    composable(BottomNavigationScreens.Coins.route) {
      CoinScreen(coinViewModel, settingsViewModel)
    }
    // TODO: Wallet screen
    /*composable(BottomNavigationScreens.Wallet.route) {
      WalletScreen()
    }*/
    composable(BottomNavigationScreens.Settings.route) {
      SettingsScreen(settingsViewModel)
    }
  }
}

@Composable
fun MainNavigationBar(
  navController: NavHostController,
  items: List<BottomNavigationScreens>
) {
  var selectedItem by remember { mutableIntStateOf(0) }
  
  NavigationBar {
    items.forEachIndexed { index, item ->
      NavigationBarItem(
        icon = { Icon(item.icon, stringResource(item.resourceId)) },
        label = { Text(stringResource(item.resourceId)) },
        selected = selectedItem == index,
        onClick = {
          selectedItem = index
          navController.navigate(item.route)
        }
      )
    }
  }
}