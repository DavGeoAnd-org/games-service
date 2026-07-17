package com.davgeoand.api.model.serializer;

import com.davgeoand.api.model.mff.Tier;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TierSerializer extends StdSerializer<Tier> {

    protected TierSerializer() {
        super(Tier.class);
    }

    @Override
    public void serialize(Tier value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getValue() + ":" + value.getLevel());
    }

}