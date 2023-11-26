package se.harrison.bitbuddy.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.style.TextOverflow
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import se.harrison.bitbuddy.data.model.Article
import se.harrison.bitbuddy.ui.shared.LoadingView
import se.harrison.bitbuddy.utils.LoadState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
  val articles = homeViewModel.articles.collectAsState().value
  val loadState = homeViewModel.loadState.collectAsState().value
  
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Latest Cryptocoin News") },
      )
    }
  ) { innerPadding ->
    HomeContent(homeViewModel, articles, loadState, innerPadding)
  }
}

@Composable
fun HomeContent(
  homeViewModel: HomeViewModel,
  articles: List<Article>,
  loadState: LoadState,
  innerPadding: PaddingValues
) {
  Box(Modifier.padding(
    top = 8.dp + innerPadding.calculateTopPadding(),
    bottom = 80.dp + innerPadding.calculateBottomPadding(), start = 8.dp, end = 8.dp)) {
    SwipeRefresh(
      state = rememberSwipeRefreshState(isRefreshing = loadState == LoadState.REFRESHING),
      onRefresh = {
        if (loadState != LoadState.REFRESHING) {
          homeViewModel.refreshArticles()
        }
      },
    ) {
      when (loadState) {
        LoadState.IDLE -> {
          homeViewModel.loadArticles()
        }
        
        LoadState.LOADING -> LoadingView()
        LoadState.LOADED, LoadState.REFRESHING -> {
          Content(homeViewModel, articles)
        }
        
        LoadState.ERROR -> {
          Text("Error please try again later")
        }
      }
    }
  }
}

@Composable
fun Content(homeViewModel: HomeViewModel, articles: List<Article>) {
  val activity = LocalContext.current as ComponentActivity
  
  LazyColumn {
    items(articles) { article ->
      NewsCard(article, onItemClick = {
        homeViewModel.onEvent(HomeEvents.ClickedNews(activity, article))
      })
    }
  }
}

@Composable
fun NewsCard(article: Article, onItemClick: () -> Unit) {
  Card(
    shape = RoundedCornerShape(11.dp),
    elevation = CardDefaults.cardElevation(
      defaultElevation = 10.dp
    ),
    modifier = Modifier
      .padding(8.dp)
      .clickable { onItemClick() }
  ) {
    Column {
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(article.imageUrl)
          .crossfade(true)
          .build(),
        contentDescription = article.title,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      )
      Text(
        text = article.title,
        style = MaterialTheme.typography.titleSmall,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(16.dp)
      )
      if (article.description.isNotEmpty()) {
        Text(
          text = article.description,
          maxLines = 4,
          overflow = TextOverflow.Ellipsis,
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.padding(top = 0.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        )
      }
    }
  }
}