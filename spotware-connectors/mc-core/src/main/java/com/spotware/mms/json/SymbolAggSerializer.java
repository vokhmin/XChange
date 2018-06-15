package com.spotware.mms.json;

import java.io.IOException;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class SymbolAggSerializer extends JsonSerializer<SymbolAggJson> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SymbolAggSerializer.class);

    private static final BigDecimal NOTHING = new BigDecimal(0.0);
    private static final String NEVER = "";

    @Override public void serialize(SymbolAggJson symbolAgg, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        for (SymbolSnapshotJson it : symbolAgg.symbols) {
            try {
                String p = it.market + "-";
                jgen.writeStringField(p + "market", it.market);
                jgen.writeStringField(p + "symbol", it.symbol.toString());
                jgen.writeStringField(p + "requestTime", it.requestTime.map(t -> t.toString()).orElse(NEVER));
                jgen.writeNumberField(p + "ask", it.ask.orElse(NOTHING));
                jgen.writeNumberField(p + "bid", it.bid.orElse(NOTHING));
                jgen.writeStringField(p + "askTradeTime", it.askTradeTime.map(t -> t.toString()).orElse(NEVER));
                jgen.writeStringField(p + "bidTradeTime", it.bidTradeTime.map(t -> t.toString()).orElse(NEVER));
            } catch (Exception e) {
                LOGGER.error("Catch an exception - {}", e.getMessage(), e);
            }
        }
        jgen.writeEndObject();
    }
}
