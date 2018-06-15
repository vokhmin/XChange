package com.spotware.mms.json;

import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.knowm.xchange.currency.CurrencyPair;

import com.spotware.mms.SymbolAggSnapshot;

import lombok.Data;

@Data
public class SymbolSnapshotJson {

    String market;
    CurrencyPair symbol;
    Optional<ZonedDateTime> requestTime;
    Optional<BigDecimal> ask;
    Optional<BigDecimal> bid;
    Optional<ZonedDateTime> askTradeTime;
    Optional<ZonedDateTime> bidTradeTime;

    public SymbolSnapshotJson(String market, CurrencyPair symbol, SymbolAggSnapshot sas) {
        this.market = market;
        this.symbol = symbol;
        this.requestTime = ofNullable(sas.getRequestTime());
        this.ask = ofNullable(sas.getAsk());
        this.bid = ofNullable(sas.getBid());
        this.askTradeTime = ofNullable(sas.getAskTradeTime());
        this.bidTradeTime = ofNullable(sas.getBidTradeTime());
    }

    public SymbolSnapshotJson(String market, CurrencyPair symbol) {
        this.market = market;
        this.symbol = symbol;
    }
}
