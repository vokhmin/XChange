package com.spotware.mms;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("Duplicates")
public class BittrexMarketDataTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(BittrexMarketDataTest.class);

  private final Class<? extends Exchange> exchangeType = BittrexExchange.class;

  @Test
  public void tickerFetchTest() throws Exception {
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exchangeType.getName());
    final MarketDataService marketDataService = exchange.getMarketDataService();
    final Params currencyPairs = (CurrencyPairsParam) () -> getCurrencyPiars();
    List<Ticker> tickers = marketDataService.getTickers(currencyPairs);
    for (Ticker it : tickers) {
      LOGGER.info("Tick: {}", it);
      assertThat(it).isNotNull();
      final Trades trades = marketDataService.getTrades(it.getCurrencyPair());
      LOGGER.info("Trades by {}: {}", it.getCurrencyPair(), trades);
    }
  }

  private Collection<CurrencyPair> getCurrencyPiars() {
    return Arrays.asList(
        new CurrencyPair("LTC", "BTC"),
        new CurrencyPair("ETH", "BTC"),
        new CurrencyPair("ZEC", "BTC"),
        new CurrencyPair("NEO", "BTC"));
  }
}
