package com.spotware.mms;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication(excludeName = "AbstractMcController")
@EnableScheduling
@Import({
  MarketsConfiguration.class,
  //        BinanceConfiguration.class,
  //        BitfinexConfiguration.class,
  BittrexConfiguration.class
})
public class ExchangeAggregatorApp {
  private static Logger LOGGER = LoggerFactory.getLogger(BinanceConnectorApp.class);

  @Autowired private ConnectorConfig config;

  @Bean
  Set<CurrencyPair> currencyPairs() {
    final Set<CurrencyPair> set =
        config.getSymbols().stream().map(it -> new CurrencyPair(it)).collect(Collectors.toSet());
    return set;
  }

  @Autowired Set<MarketDataSource> sources;

  @Bean
  MarketDataTasks tasks(List<MarketDataSource> sources) {
    return new MarketDataTasks(sources);
  }

  @Bean
  public TaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(sources.size());
    executor.setMaxPoolSize(sources.size());
    executor.setThreadNamePrefix("exec");
    executor.initialize();
    return executor;
  }

  public static void main(String[] args) {
    long startMillis = System.currentTimeMillis();
    String label = getComponentName();
    String version = getComponentVersion();

    LOGGER.info("| SERVER_STARTING | Starting {} Version {}...", label, version);

    SpringApplication app = new SpringApplication(ExchangeAggregatorApp.class);
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
