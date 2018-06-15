package com.spotware.mms;

import java.time.ZonedDateTime;
import java.util.Map;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString(exclude = {"trades"})
public class MarketDataSnapshot {

    private final ZonedDateTime utcDateTime;
    private final Map<CurrencyPair, Ticker> tickers;
    private final Map<CurrencyPair, Trades> trades;

}