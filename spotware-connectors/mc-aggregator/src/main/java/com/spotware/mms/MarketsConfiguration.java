package com.spotware.mms;

import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarketsConfiguration {

  @Bean
  MarketDataSource poloneixMarketDataSource(Set<CurrencyPair> currencyPairs) {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final MarketDataSource source = new MarketDataSource("Poloneix", marketDataService);
    source.setCurrencyPairs(currencyPairs);
    return source;
  }

  @Bean
  MarketDataSource krakenMarketDataSource(Set<CurrencyPair> currencyPairs) {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final MarketDataSource source = new MarketDataSource("Kraken", marketDataService);
    source.setCurrencyPairs(currencyPairs);
    return source;
  }

  @Bean
  MarketDataSource gdaxMarketDataSource(Set<CurrencyPair> currencyPairs) {
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final MarketDataSource source = new MarketDataSource("GDAX", marketDataService);
    source.setCurrencyPairs(currencyPairs);
    return source;
  }
}
