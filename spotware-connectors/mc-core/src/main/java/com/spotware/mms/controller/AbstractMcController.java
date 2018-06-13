package com.spotware.mms.controller;

import javax.servlet.http.HttpServletRequest;

import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spotware.mms.MarketDataSnapshot;
import com.spotware.mms.MarketDataSource;
import com.spotware.mms.SymbolAggSnapshot;
import com.spotware.mms.SymbolSnapshot;

import reactor.core.publisher.Mono;

public abstract class AbstractMcController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMcController.class);

  @Value("${rate}")
  private Long delta;

  @GetMapping(value = "snapshot/{symbol}")
  @ResponseBody
  Mono<SymbolSnapshot> symbol(@PathVariable String symbol) {
    final CurrencyPair pair = new CurrencyPair(symbol.replace("-", "/"));
    final SymbolSnapshot result = getSymbolSnapshot(pair, getSource().getSnapshot());
    return Mono.just(result);
  }

  private SymbolSnapshot getSymbolSnapshot(CurrencyPair pair, MarketDataSnapshot snapshot) {
    return SymbolSnapshot.builder()
        .utcDateTime(snapshot.getUtcDateTime())
        .symbol(pair)
        .ticker(snapshot.getTickers().get(pair))
        .trades(snapshot.getTrades().get(pair))
        .build();
  }

  @GetMapping(value = "agg/{symbol}")
  @ResponseBody
  Mono<SymbolAggSnapshot> aggSymbol(@PathVariable String symbol) {
    final CurrencyPair pair = new CurrencyPair(symbol.replace("-", "/"));
    final MarketDataSnapshot snapshot = getSource().getSnapshot();
    final SymbolAggSnapshot result =
        SymbolAggSnapshot.builder()
            .snapshot(getSymbolSnapshot(pair, snapshot))
            .tradeTimePredicate(
                t ->
                    snapshot.getUtcDateTime().toEpochSecond() * 1000 - t.getTimestamp().getTime()
                        < delta)
            .build();
    return Mono.just(result);
  }

  @RequestMapping(value = "/log/**")
  @ResponseBody
  public String logMarketDataSnapshot(HttpServletRequest request) {
    LOGGER.debug(
        "Web service request#: {}, method: {}", request.getRequestURI(), request.getMethod());
    final MarketDataSnapshot snapshot = getSource().getSnapshot();
    if (snapshot == null) {
      return "There's not any data, try later ...";
    }
    return snapshot.getTickers().toString() + "\n" + snapshot.getTrades() + "\nOk";
  }

  protected abstract MarketDataSource getSource();
}
