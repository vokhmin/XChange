package com.spotware.mms;

import com.spotware.mms.controller.AbstractMcController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/binance")
public class McBinanceController extends AbstractMcController {

  private final MarketDataSource source;

  public McBinanceController(@Qualifier("binanceMarketDataSource") MarketDataSource source) {
    this.source = source;
  }

  @Override
  protected MarketDataSource getSource() {
    return source;
  }
}
