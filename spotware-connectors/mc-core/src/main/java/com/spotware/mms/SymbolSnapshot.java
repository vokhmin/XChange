package com.spotware.mms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
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
public class SymbolSnapshot implements Serializable {

  private final ZonedDateTime utcDateTime;
  private final CurrencyPair symbol;
  private final Ticker ticker;
  private final Trades trades;
}
