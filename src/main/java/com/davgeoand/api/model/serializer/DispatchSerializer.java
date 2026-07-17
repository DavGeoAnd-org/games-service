package com.davgeoand.api.model.serializer;

import com.davgeoand.api.model.mff.Dispatch;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class DispatchSerializer extends StdSerializer<Dispatch> {

    protected DispatchSerializer() {
        super(Dispatch.class);
    }

    @Override
    public void serialize(Dispatch value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getSector() + ":" + value.getLevel());
    }

}