package com.spotware.mms;

import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BittrexConfiguration {

  @Bean
  MarketDataSource bittrexMarketDataSource(Set<CurrencyPair> currencyPairs) {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(BittrexExchange.class.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final MarketDataSource source = new MarketDataSource("Bittrex", marketDataService);
    source.setCurrencyPairs(currencyPairs);
    return source;
  }

  //    @Bean
  //    McBittrexController bittrexController(MarketDataSource source) {
  //        return new McBittrexController(source);
  //    }
}
