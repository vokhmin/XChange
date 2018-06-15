package com.spotware.mms.controller;

import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spotware.mms.MarketDataSnapshot;
import com.spotware.mms.MarketDataSource;
import com.spotware.mms.SymbolAggSnapshot;
import com.spotware.mms.SymbolSnapshot;
import com.spotware.mms.json.SymbolAggJson;

import reactor.core.publisher.Mono;

@Controller
public class McAggController {
    private static final Logger LOGGER = LoggerFactory.getLogger(McAggController.class);

    @Value("${rate}")
    private Long delta;
    @Autowired
    private List<MarketDataSource> sources;

    @GetMapping(value = "agg/{symbol}")
    @ResponseBody
    Mono<SymbolAggJson> aggSymbol(@PathVariable String symbol) {
        final CurrencyPair pair = new CurrencyPair(symbol.replace("-", "/"));
        final SymbolAggJson json = new SymbolAggJson();
        for (MarketDataSource source : getSources()) {
            final MarketDataSnapshot snapshot = source.getSnapshot();
            if (snapshot == null) {
                LOGGER.warn("We don't have any data for symbol " + pair);
                json.addEmpty(source.market, pair);
            } else {
                json.add(source.market,
                        pair,
                        SymbolAggSnapshot.builder()
                                .snapshot(getSymbolSnapshot(pair, snapshot))
                                .tradeTimePredicate(t ->
                                        snapshot.getUtcDateTime().toEpochSecond() * 1000 - t.getTimestamp().getTime() < delta)
                                .build());
            }
        }
        return Mono.just(json);
    }

    private List<MarketDataSource> getSources() {
        return sources;
    }

    private SymbolSnapshot getSymbolSnapshot(CurrencyPair pair, MarketDataSnapshot snapshot) {
        return SymbolSnapshot.builder()
                .utcDateTime(snapshot.getUtcDateTime())
                .symbol(pair)
                .ticker(snapshot.getTickers().get(pair))
                .trades(snapshot.getTrades().get(pair))
                .build();
    }

}
