package com.spotware.mms;

import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BinanceConfiguration {

  @Bean
  MarketDataSource binanceMarketDataSource(Set<CurrencyPair> currencyPairs) {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final MarketDataSource source = new MarketDataSource(marketDataService);
    source.setCurrencyPairs(currencyPairs);
    return source;
  }

  //    @Bean
  //    McBinanceController binanceController(MarketDataSource source) {
  //        return new McBinanceController(source);
  //    }
}
