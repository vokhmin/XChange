package com.spotware.mms;

import static java.util.Collections.unmodifiableMap;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarketDataSource {
  private static final Logger LOGGER = LoggerFactory.getLogger(MarketDataSource.class);

  private final MarketDataService marketDataService;

  private Set<CurrencyPair> currencyPairs = new ConcurrentSkipListSet<>();
  private volatile MarketDataSnapshot snapshot;

  public MarketDataSource(MarketDataService service) {
    marketDataService = service;
  }

  public MarketDataSnapshot read(final Set<CurrencyPair> currencyPairs) {
    final Map<CurrencyPair, Ticker> tickers = new HashMap<>();
    final Map<CurrencyPair, Trades> trades = new HashMap<>();
    try {
      for (CurrencyPair pair : currencyPairs) {
        tickers.put(pair, marketDataService.getTicker(pair));
        trades.put(pair, marketDataService.getTrades(pair));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return MarketDataSnapshot.builder()
        .utcDateTime(ZonedDateTime.now(ZoneOffset.UTC))
        .tickers(unmodifiableMap(tickers))
        .trades(unmodifiableMap(trades))
        .build();
  }

  public void refreshMarektData() {
    LOGGER.info("Try to read data from a market");
    try {
      snapshot = this.read(currencyPairs);
      LOGGER.info("MarketData was read: \n{}", snapshot);
    } catch (Exception e) {
      LOGGER.error("Couldn't read data from a market", e);
    }
  }

  public Set<CurrencyPair> getCurrencyPairs() {
    return new HashSet<>(currencyPairs);
  }

  public void setCurrencyPairs(Set<CurrencyPair> currencyPairs) {
    this.currencyPairs.addAll(currencyPairs);
  }

  public MarketDataSnapshot getSnapshot() {
    return snapshot;
  }
}
