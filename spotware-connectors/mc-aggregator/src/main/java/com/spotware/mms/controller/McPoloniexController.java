package com.spotware.mms.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spotware.mms.MarketDataSource;

@Controller
@RequestMapping("/poloniex")
public class McPoloniexController extends AbstractMcController {

  private final MarketDataSource source;

  public McPoloniexController(@Qualifier("poloniexMarketDataSource") MarketDataSource source) {
    this.source = source;
  }

  @Override
  protected MarketDataSource getSource() {
    return source;
  }
}
