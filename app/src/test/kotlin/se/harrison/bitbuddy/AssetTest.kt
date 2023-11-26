package se.harrison.bitbuddy

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import se.harrison.bitbuddy.data.model.Asset
import se.harrison.bitbuddy.data.model.Currency


class AssetTest {
  @Test
  fun `test getFormattedSymbol`() {
    val asset = Asset("BTCEUR", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0)
    assertEquals("BTC", asset.getFormattedSymbol())
  }
  
  @Test
  fun `test getSymbolLogoUrl`() {
    val asset = Asset("BTCEUR", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0)
    assertEquals("https://cdn.jsdelivr.net/gh/spothq/cryptocurrency-icons@master/32/icon/btc.png", asset.getSymbolLogoUrl())
  }
  
  @Test
  fun `test getFormattedPriceChange`() {
    val asset = Asset("BTCEUR", 10.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0)
    val currency: Currency = Mockito.mock(Currency::class.java)
    whenever(currency.code).thenReturn("$")
    whenever(currency.rate).thenReturn(0.5)
    whenever(currency.prefix).thenReturn(true)
    assertEquals("+$5 (20.0%)", asset.getFormattedPriceChange(currency))
  }
  
  @Test
  fun `test negative getFormattedPriceChange`() {
    val asset = Asset("BTCEUR", -10.0, -20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 0)
    val currency: Currency = Mockito.mock(Currency::class.java)
    whenever(currency.code).thenReturn("$")
    whenever(currency.rate).thenReturn(0.5)
    whenever(currency.prefix).thenReturn(true)
    assertEquals("-$5 (-20.0%)", asset.getFormattedPriceChange(currency))
  }
  
  @Test
  fun `test getFormattedVolume`() {
    val asset = Asset("BTCEUR", 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 100.0, 100.0, 0.0, 0, 0, 0, 0, 0)
    val currency: Currency = Mockito.mock(Currency::class.java)
    whenever(currency.code).thenReturn("$")
    whenever(currency.rate).thenReturn(0.5)
    whenever(currency.prefix).thenReturn(true)
    assertEquals("$50", asset.getFormattedVolume(currency))
  }
}