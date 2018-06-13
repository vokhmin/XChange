package com.spotware.mms;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;

public class MarketDataTasks {
  private static final Logger LOGGER = LoggerFactory.getLogger(MarketDataTasks.class);

  private final List<MarketDataSource> sources;

  @Autowired private TaskExecutor taskExecutor;

  public MarketDataTasks(List<MarketDataSource> sources) {
    this.sources = sources;
  }

  @Scheduled(fixedRateString = "${rate}")
  public void refreshMarektData() {
    for (MarketDataSource source : sources) {
      taskExecutor.execute(() -> source.refreshMarektData());
    }
  }
}
