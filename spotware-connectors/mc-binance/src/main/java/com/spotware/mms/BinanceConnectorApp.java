package com.spotware.mms;

import java.util.HashSet;
import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BinanceConnectorApp {
  private static Logger LOGGER = LoggerFactory.getLogger(BinanceConnectorApp.class);

  @Autowired private ConnectorConfig config;

  @Bean
  MarketDataSource marketDataSource() {
    try {
      final Exchange exchange =
          ExchangeFactory.INSTANCE.createExchange(Class.forName(config.getExchange()).getName());
      final MarketDataService marketDataService = exchange.getMarketDataService();
      final MarketDataSource source = new MarketDataSource(marketDataService);
      source.setCurrencyPairs(currencyPairs());
      return source;
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Bean
  Set<CurrencyPair> currencyPairs() {
    final HashSet<CurrencyPair> pairs = new HashSet<>();
    pairs.add(new CurrencyPair(config.getSymbols().get(0)));
    return pairs;
  }

  public static void main(String[] args) {
    long startMillis = System.currentTimeMillis();
    String label = getComponentName();
    String version = getComponentVersion();

    LOGGER.info("| SERVER_STARTING | Starting {} Version {}...", label, version);

    SpringApplication app = new SpringApplication(BinanceConnectorApp.class);
    app.setLogStartupInfo(false);
    app.addListeners(new ApplicationPidFileWriter());
    app.run(args);

    LOGGER.info(
        "| SERVER_STARTED | {} is started in {} seconds.",
        label,
        ((System.currentTimeMillis() - startMillis) / 1000));
  }

  public static String getComponentName() {
    return "Binance Market Connector";
  }

  public static String getComponentVersion() {
    //        ManifestUtils.getComponentVersion()
    return "0.1";
  }
}
