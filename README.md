# BitBuddy

A sample cryptocurrency app

This app is build with the MVVM architecture, repository pattern (with no persistance yet) and clean architecture style event handling for interface interactions.

A very minimum set of dependencies were included to keep the app clean:

 - Koin: Dependency Injection
 - Retrofit: HTTP api consumption, both XML and JSON
 - Coil: Image loading library
 - (Compose, Timber, JUnit and Mockito being the others)

The app uses 3 apis defined in the NetworkModule:
 - Coindesk News RSS feed (XML) (www.coindesk.com)
 - Binance 24h currency tracker api (JSON) (www.binance.com)
 - ExchangeRates api (JSON) (http://api.exchangeratesapi.io)

The last of which is accessed through a proxy with a long cache so as not to burn through 1000 requests limit the free usage tier is given.

The App icon comes from an artist named Freepik from https://www.flaticon.com/, all rights reserved.

TODO:
 - A better source for up to date currency exchange rates
 - Persistance of currency choice
 - Wallet screen
 - Detail view for coins
 - Charts
 - Tickers
