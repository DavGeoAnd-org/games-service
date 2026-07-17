package com.davgeoand.api.model.serializer;

import com.davgeoand.api.model.mff.Dispatch;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class DispatchDeserializer extends StdDeserializer<Dispatch> {

    protected DispatchDeserializer() {
        super(Dispatch.class);
    }

    @Override
    public Dispatch deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String id = p.getText();
        int separatorIndex = id.indexOf(":");
        return new Dispatch(Integer.parseInt(id.substring(0, separatorIndex)),
                Integer.parseInt(id.substring(separatorIndex + 1)));
    }
}
