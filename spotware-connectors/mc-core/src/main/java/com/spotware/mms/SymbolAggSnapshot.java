package com.spotware.mms;

import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.function.Predicate;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Value;

@Value
@JsonIgnoreProperties(
        ignoreUnknown = true,
        value = {"snapshot", "tradeTimePredicate", "askTrade", "bidTrade"})
@JsonPropertyOrder(alphabetic = true)
public class SymbolAggSnapshot implements Serializable {

    private final SymbolSnapshot snapshot;
    private final Predicate<Trade> tradeTimePredicate;
    private final Optional<Trade> bidTrade;
    private final Optional<Trade> askTrade;

    @lombok.Builder(builderClassName = "Builder")
    public SymbolAggSnapshot(SymbolSnapshot snapshot, Predicate<Trade> tradeTimePredicate) {
        this.snapshot = snapshot;
        this.tradeTimePredicate = tradeTimePredicate;
        this.bidTrade =
                snapshot
                        .getTrades()
                        .getTrades()
                        .stream()
                        .filter(t -> t.getType() == BID && tradeTimePredicate.test(t))
                        .findAny();
        this.askTrade =
                snapshot
                        .getTrades()
                        .getTrades()
                        .stream()
                        .filter(t -> t.getType() == ASK && tradeTimePredicate.test(t))
                        .findAny();
    }

    public ZonedDateTime getRequestTime() {
        return snapshot.getUtcDateTime();
    }

    public CurrencyPair getCurrencyPair() {
        return snapshot.getTicker().getCurrencyPair();
    }

    public BigDecimal getOpen() {
        return snapshot.getTicker().getOpen();
    }

    public BigDecimal getLast() {
        return snapshot.getTicker().getLast();
    }

    public BigDecimal getBid() {
        return snapshot.getTicker().getBid();
    }

    public BigDecimal getAsk() {
        return snapshot.getTicker().getAsk();
    }

    public BigDecimal getHigh() {
        return snapshot.getTicker().getHigh();
    }

    public BigDecimal getLow() {
        return snapshot.getTicker().getLow();
    }

    public BigDecimal getVwap() {
        return snapshot.getTicker().getVwap();
    }

    public BigDecimal getVolume() {
        return snapshot.getTicker().getVolume();
    }

    public BigDecimal getQuoteVolume() {
        return snapshot.getTicker().getQuoteVolume();
    }

    public BigDecimal getBidSize() {
        return snapshot.getTicker().getBidSize();
    }

    public BigDecimal getAskSize() {
        return snapshot.getTicker().getAskSize();
    }

    public ZonedDateTime getSpotTimestamp() {
        final Date ts;
        if ((ts = snapshot.getTicker().getTimestamp()) == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(ts.toInstant(), ZoneOffset.UTC);
    }

    public ZonedDateTime getBidTradeTime() {
        return bidTrade
                .map(t -> ZonedDateTime.ofInstant(t.getTimestamp().toInstant(), ZoneOffset.UTC))
                .orElse(null);
    }

    public ZonedDateTime getAskTradeTime() {
        return askTrade
                .map(t -> ZonedDateTime.ofInstant(t.getTimestamp().toInstant(), ZoneOffset.UTC))
                .orElse(null);
    }
}
