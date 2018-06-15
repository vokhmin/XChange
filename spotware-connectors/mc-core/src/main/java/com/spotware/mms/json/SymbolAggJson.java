package com.spotware.mms.json;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.spotware.mms.SymbolAggSnapshot;

@JsonSerialize(using = SymbolAggSerializer.class)
public class SymbolAggJson {

    final List<SymbolSnapshotJson> symbols = new ArrayList<>();

    public void add(String market, CurrencyPair symbol, SymbolAggSnapshot snapshot) {
        final SymbolSnapshotJson json = new SymbolSnapshotJson(market, symbol, snapshot);
        symbols.add(json);
    }

    public void addEmpty(String market, CurrencyPair symbol) {
        final SymbolSnapshotJson json = new SymbolSnapshotJson(market, symbol);
        symbols.add(json);
    }
}
