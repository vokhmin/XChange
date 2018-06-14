package com.spotware.mms;

import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BitfinexConfiguration {

  @Bean
  MarketDataSource bitfinexMarketDataSource(Set<CurrencyPair> currencyPairs) {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final MarketDataSource source = new MarketDataSource("Bitfinex", marketDataService);
    source.setCurrencyPairs(currencyPairs);
    return source;
  }

  //    @Bean
  //    McBitfinexController bitfinexController(@Qualifier("bitfinexMarketDataSource")
  // MarketDataSource source) {
  //        return new McBitfinexController(source);
  //    }
}
