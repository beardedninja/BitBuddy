package se.harrison.bitbuddy.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import se.harrison.bitbuddy.data.model.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
  // List of currencies
  val currencies = settingsViewModel.supportedCurrencies()
  
  // State for DropdownMenu visibility
  var expanded by remember { mutableStateOf(false) }
  
  // State for selected currency
  val selectedCurrency = settingsViewModel.currency.collectAsState().value
  
  Scaffold(
    topBar = {
      TopAppBar(title = {
        Text(text = "Settings")
      })
    }
  ) { innerPadding ->
    SettingsContent(
      currencies = currencies,
      selectedCurrency = selectedCurrency,
      settingsViewModel = settingsViewModel,
      expanded = expanded,
      onExpandedChange = { expanded = it },
      innerPadding = innerPadding
    )
  }
  
}

@Composable
fun SettingsContent(
  currencies: List<Currency>,
  selectedCurrency: Currency,
  settingsViewModel: SettingsViewModel,
  expanded: Boolean,
  onExpandedChange: (Boolean) -> Unit,
  innerPadding: PaddingValues
) {
  Column(Modifier.padding(innerPadding)) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(80.dp)
        .padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(text = "Select Currency", style = MaterialTheme.typography.titleMedium)
      Box {
        OutlinedButton(
          onClick = { onExpandedChange(true) },
          modifier = Modifier
            .height(48.dp)
            .width(100.dp)
        ) {
          Text(selectedCurrency.symbol, style = MaterialTheme.typography.titleMedium)
        }
        DropdownMenu(
          expanded = expanded,
          onDismissRequest = { onExpandedChange(false) },
          modifier = Modifier
            .width(200.dp)
            .padding(16.dp)
        ) {
          currencies.forEach { currency ->
            DropdownMenuItem(
              text = { Text(text = "${currency.symbol} (${currency.name})") },
              onClick = {
                settingsViewModel.updateCurrency(currency)
                onExpandedChange(false)
              },
              modifier = Modifier.align(Alignment.End)
            )
          }
        }
      }
    }
  }
}