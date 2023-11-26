package se.harrison.bitbuddy.ui.coin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import se.harrison.bitbuddy.R
import se.harrison.bitbuddy.data.model.Coin
import se.harrison.bitbuddy.data.model.Currency
import se.harrison.bitbuddy.ui.settings.SettingsViewModel
import se.harrison.bitbuddy.ui.shared.LoadingView
import se.harrison.bitbuddy.utils.LoadState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinScreen(coinViewModel: CoinViewModel, settingsViewModel: SettingsViewModel) {
  val coins = coinViewModel.coins.collectAsState().value
  val loadState = coinViewModel.loadState.collectAsState().value
  val currency = settingsViewModel.currency.collectAsState().value
  
  LaunchedEffect(Unit) {
    coinViewModel.loadCoins(currency)
  }
  
  val imageLoader = ImageLoader.Builder(LocalContext.current)
    .error(R.drawable.ic_broken_image_32)
    .build()
  
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Coin Performance - Last 24 hours") },
      )
    }
  ) { innerPadding ->
    CoinContent(coinViewModel, coins, loadState, currency, imageLoader, innerPadding)
  }
}

@Composable
fun CoinContent(
  coinViewModel: CoinViewModel,
  coins: List<Coin>,
  loadState: LoadState,
  currency: Currency,
  imageLoader: ImageLoader,
  innerPadding: PaddingValues
) {
  Box(Modifier.padding(innerPadding)) {
    SwipeRefresh(
      state = rememberSwipeRefreshState(isRefreshing = loadState == LoadState.REFRESHING),
      onRefresh = {
        if (loadState != LoadState.REFRESHING) {
          coinViewModel.refreshCoins(currency)
        }
      },
    ) {
      when (loadState) {
        LoadState.IDLE -> {
          coinViewModel.loadCoins(currency)
        }
        
        LoadState.LOADING -> {
          LoadingView()
        }
        
        LoadState.REFRESHING, LoadState.LOADED -> {
          Content(coins, imageLoader)
        }
        
        LoadState.ERROR -> {
          Text("Error please try again later")
        }
      }
    }
  }
}

@Composable
fun Content(coins: List<Coin>, imageLoader: ImageLoader) {
  LazyColumn(
    modifier = Modifier
      .padding(top = 8.dp, bottom = 80.dp, start = 8.dp, end = 8.dp)
  ) {
    items(coins) { coin ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        Column(Modifier.padding(8.dp)) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                  .data(coin.logoUrl)
                  .crossfade(false)
                  .build(),
                contentDescription = coin.symbol,
                modifier = Modifier
                  .size(40.dp)
                  .padding(end = 8.dp),
                imageLoader = imageLoader,
              )
              Text(
                coin.symbol,
                style = MaterialTheme.typography.titleMedium
              )
            }
            Text(
              coin.priceChange,
              style = MaterialTheme.typography.titleMedium,
              color = if (coin.priceChangeRaw < 0) Color.Red else Color.Green,
              modifier = Modifier.padding(end = 8.dp, start = 8.dp)
            )
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(24.dp)
              .padding(4.dp),
            horizontalArrangement = Arrangement.End
          ) {
            Text(
              "Volume: ${coin.volume}",
              style = MaterialTheme.typography.bodyMedium
            )
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(24.dp)
              .padding(4.dp),
            horizontalArrangement = Arrangement.End
          ) {
            Text(
              "Open Price: ${coin.openPrice}",
              style = MaterialTheme.typography.bodyMedium
            )
          }
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(24.dp)
              .padding(4.dp),
            horizontalArrangement = Arrangement.End
          ) {
            Text("Last Price: ${coin.lastPrice}", style = MaterialTheme.typography.bodyMedium)
          }
        }
      }
    }
  }
}