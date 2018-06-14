package com.spotware.mms.controller;

import com.spotware.mms.MarketDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gdax")
public class McGDAXController extends AbstractMcController {

  private final MarketDataSource source;

  public McGDAXController(@Qualifier("gdaxMarketDataSource") MarketDataSource source) {
    this.source = source;
  }

  @Override
  protected MarketDataSource getSource() {
    return source;
  }
}
