package com.spotware.mms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;

@Value
@Builder(builderClassName = "Builder")
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketDataSnapshot {

  private final ZonedDateTime utcDateTime;
  private final Map<CurrencyPair, Ticker> tickers;
  private final Map<CurrencyPair, Trades> trades;
}
