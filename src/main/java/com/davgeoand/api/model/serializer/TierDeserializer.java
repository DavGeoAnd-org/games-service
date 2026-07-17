package com.davgeoand.api.model.serializer;

import com.davgeoand.api.model.mff.Tier;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TierDeserializer extends StdDeserializer<Tier> {

    protected TierDeserializer() {
        super(Tier.class);
    }

    @Override
    public Tier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String id = p.getText();
        int separatorIndex = id.indexOf(":");
        return new Tier(id.substring(0, separatorIndex), Integer.parseInt(id.substring(separatorIndex + 1)));
    }
}