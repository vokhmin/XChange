package com.spotware.mms;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class ConnectorConfig {

  private String exchange;
  private Long rate;
  private List<String> symbols;

  public String getExchange() {
    return exchange;
  }

  public void setExchange(String exchange) {
    this.exchange = exchange;
  }

  public List<String> getSymbols() {
    return symbols;
  }

  public void setSymbols(List<String> symbols) {
    this.symbols = symbols;
  }

  public Long getRate() {
    return rate;
  }

  public void setRate(Long rate) {
    this.rate = rate;
  }

  @Configuration
  @ConfigurationProperties
  public static class SymbolsConfig {

    private String base;
    private String quote;

    public String getBase() {
      return base;
    }

    public void setBase(String base) {
      this.base = base;
    }

    public String getQuote() {
      return quote;
    }

    public void setQuote(String quote) {
      this.quote = quote;
    }
  }
}
